package com.fondzazdr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.samir.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {

	private static final String queryzemiidinaziv = "http://linkeddata.finki.ukim.mk/sparql?default-graph-uri=http%3A%2F%2Fwww.fzo.org.mk%2Fontology%2Fhifm%23&should-sponge=grab-all-seealso&query=PREFIX+d%3A+%3Chttp%3A%2F%2Fwifo5-04.informatik.uni-mannheim.de%2Fdrugbank%2Fresource%2Fdrugbank%2F%3E%0D%0APREFIX+hifm%3A+%3Chttp%3A%2F%2Fwww.fzo.org.mk%2Fontology%2Fhifm%23%3E%0D%0ASELECT+DISTINCT+%3FID++%3FNazivnaLek+%0D%0AWHERE++%0D%0A%7B%0D%0A%3Fx+hifm%3Aid+%3FID%3B%0D%0A+++d%3AbrandName+%3FNazivnaLek.%0D%0A+++%0D%0A++%0D%0A+%0D%0A+++%0D%0A%0D%0A%0D%0A++%0D%0A+++%0D%0A%7D&debug=on&timeout=&format=JSON";
	private static final String OB_rezultati = "results";
	private static final String LISTA_rezultati = "bindings";
	private static final String ID_OBJEKT = "ID";
	private static final String ID_vrednost = "value";
	private static final String OBJEKT_NAZIVNALEK = "NazivnaLek";
	private static final String NAZIVNALEK_vrednost = "value";

	EditText inputSearch;
	List<Item> arrayOfList;

	ListView listView;
	NewsRowAdapter objAdapter;

	private ListView listView1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		LinearLayout header = (LinearLayout) findViewById(R.id.header);
		header.setVisibility(View.INVISIBLE);
		EditText search = (EditText) findViewById(R.id.inputSearch);
		search.setVisibility(View.INVISIBLE);

		listView = (ListView) findViewById(R.id.listview);
		listView.setOnItemClickListener(this);

		arrayOfList = new ArrayList<Item>();

		inputSearch = (EditText) findViewById(R.id.inputSearch);

		if (Utils.isNetworkAvailable(MainActivity.this)) {
			new MyTask().execute(queryzemiidinaziv);
		} else {
			showToast("For using this Application Internet Connection must Exist!!!");
			MainActivity.this.finish();
		}

	}

	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			return Utils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
			if (null == result || result.length() == 0) {
				showToast("no data!!!");
				MainActivity.this.finish();
			} else {
				try {
					JSONObject mainJson = new JSONObject(result);
					JSONObject v = mainJson.getJSONObject(OB_rezultati);
					JSONArray jsonArray = v.getJSONArray(LISTA_rezultati);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject objJson = jsonArray.getJSONObject(i);

						Item objItem = new Item();

						// zemi vrednost na id na lekot
						JSONObject id = objJson.getJSONObject(ID_OBJEKT);
						String vrednostid = id.getString(ID_vrednost);						
						objItem.setid(vrednostid);

						// zemi ime na lekot
						JSONObject idlek = objJson
								.getJSONObject(OBJEKT_NAZIVNALEK);
						String vrednostlek = idlek
								.getString(NAZIVNALEK_vrednost);
						objItem.setnaziv(vrednostlek);
						arrayOfList.add(objItem);

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// /posle vcituvanje
				TextView textloading = (TextView) findViewById(R.id.textzapol);
				textloading.setVisibility(View.INVISIBLE);
				ImageView newImg = (ImageView) findViewById(R.id.imageView1);
				newImg.setVisibility(View.INVISIBLE);
				LinearLayout header = (LinearLayout) findViewById(R.id.header);
				header.setVisibility(View.VISIBLE);
				EditText search = (EditText) findViewById(R.id.inputSearch);
				search.setVisibility(View.VISIBLE);
				TextView fondnaslov = (TextView) findViewById(R.id.fond);
				fondnaslov.setVisibility(View.INVISIBLE);

				inputSearch.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence cs, int arg1,
							int arg2, int arg3) {
						// When user changed the Text

					}

					@Override
					public void beforeTextChanged(CharSequence arg0, int arg1,
							int arg2, int arg3) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable arg0) {
						// TODO Auto-generated method stub
						String text = inputSearch.getText().toString()
								.toLowerCase();
						MainActivity.this.objAdapter.filterr(text);
					}
				});

				setAdapterToListview();

			}

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// String name = ((TextView)
		// view.findViewById(R.id.nazivnalekot)).getText().toString();
		String idlek = ((TextView) view.findViewById(R.id.idlek)).getText()
				.toString();
		Intent in = new Intent(getApplicationContext(),
				SingleMenuItemActivity.class);
		// in.putExtra(NAZIVNALEK_vrednost, name);
		in.putExtra(ID_vrednost, idlek);

		startActivity(in);

	}

	public void setAdapterToListview() {
		objAdapter = new NewsRowAdapter(MainActivity.this, R.layout.row,
				arrayOfList);
		// View header = (View)getLayoutInflater().inflate(R.layout.header,
		// null);
		// listView.addHeaderView(header);
		listView.setAdapter(objAdapter);

	}

	public void showToast(String msg) {
		Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
	}

}