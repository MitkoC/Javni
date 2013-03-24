package com.example.javnimobilnimrezilab1;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Spinner spinnerctrl;
	Locale myLocale;
	private ListView listView1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		String locale = getResources().getConfiguration().locale.getDisplayName();
	
			Vreme vreme_data[] = new Vreme[]
			        {
			            new Vreme(R.drawable.oblacno, "Skopje:Cloudy"),
			            new Vreme(R.drawable.soncevo, "Bitola:Sunny"),
			            new Vreme(R.drawable.soncevo, "Gevgelija:Sunny"),
			            new Vreme(R.drawable.oblacno, "Kumanovo:Cloudy"),
			        };
			 Customadapter adapter = new Customadapter(this, 
		                R.layout.listalay, vreme_data);
		        
		        
		        listView1 = (ListView)findViewById(R.id.listView1);
		         
		        View header = (View)getLayoutInflater().inflate(R.layout.naslov, null);
		        listView1.addHeaderView(header);
		        
		        listView1.setAdapter(adapter);
		
		       
		     
		        spinnerctrl = (Spinner) findViewById(R.id.spinner1);
				spinnerctrl.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> parent, View view,
							int pos, long id) {

						if (pos == 1) {
		                    
							Toast.makeText(parent.getContext(),
									"Избравте Македонски", Toast.LENGTH_SHORT)
									.show();
							setLocale("mk");
							
							
						} else if (pos == 2) {

							Toast.makeText(parent.getContext(),
									"You have selected English", Toast.LENGTH_SHORT)
									.show();
							setLocale("en");
						} 

						
						
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}

				});
				
				
				
				
			}
			

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	public void setLocale(String lang) {

		myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
		Intent refresh = new Intent(this, MainActivity.class);
		startActivity(refresh);
	}
	
	
	@Override
	protected void onStart() {
	
	super.onStart();
	Toast.makeText(this, "Стартување на апликацијата", Toast.LENGTH_LONG).show();
	}
	@Override
	protected void onPause() {
	super.onPause();
	Toast.makeText(this, "Паузирање)", Toast.LENGTH_LONG).show();
	}
	@Override
	protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	Toast.makeText(this, "Продолжи", Toast.LENGTH_LONG).show();
	}
	@Override
	protected void onStop() {
	
	super.onStop();
	Toast.makeText(this, "Стопирање", Toast.LENGTH_LONG).show();
	}
	@Override
	protected void onDestroy() {
	
	super.onDestroy();
	Toast.makeText(this, "Бришење од меморија", Toast.LENGTH_LONG).show();
	}
	
	
	
	
}
