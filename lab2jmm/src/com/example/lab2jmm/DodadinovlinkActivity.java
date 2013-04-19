package com.example.lab2jmm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DodadinovlinkActivity extends Activity {

	Button btnSubmit;
	Button btnCancel;
	EditText txtUrl;
	TextView lblMessage;

	RSSParser rssParser = new RSSParser();

	RSSFeed rssFeed;

	
	private ProgressDialog pDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dodadilink);

		
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		txtUrl = (EditText) findViewById(R.id.txtUrl);
		lblMessage = (TextView) findViewById(R.id.lblMessage);

		
		btnSubmit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String url = txtUrl.getText().toString();

				
				Log.d("URL Length", "" + url.length());
				
				if (url.length() > 0) {
					lblMessage.setText("");
					String urlPattern = "^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";
					if (url.matches(urlPattern)) {
					
						new loadRSSFeed().execute(url);
					} else {
						
						lblMessage.setText("внеси валиден линк");
					}
				} else {
					
					lblMessage.setText("Внеси линк");
				}

			}
		});

	
		btnCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

	
	class loadRSSFeed extends AsyncTask<String, String, String> {

		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DodadinovlinkActivity.this);
			pDialog.setMessage("Превземање ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

	
		@Override
		protected String doInBackground(String... args) {
			String url = args[0];
			rssFeed = rssParser.getRSSFeed(url);
			Log.d("rssFeed", " "+ rssFeed);
			if (rssFeed != null) {
				Log.e("RSS URL",
						rssFeed.getTitle() + "" + rssFeed.getLink() + ""
								+ rssFeed.getDescription() + ""
								+ rssFeed.getLanguage());
				RSSDatabaseHandler rssDb = new RSSDatabaseHandler(
						getApplicationContext());
				Website site = new Website(rssFeed.getTitle(), rssFeed.getLink(), rssFeed.getRSSLink(),
						rssFeed.getDescription());
				rssDb.addSite(site);
				Intent i = getIntent();
				
				setResult(100, i);
				finish();
			} else {
			
				runOnUiThread(new Runnable() {
					public void run() {
						lblMessage.setText("Линкот не е пронајден!!");
					}
				});
			}
			return null;
		}

		
		protected void onPostExecute(String args) {
			
			pDialog.dismiss();
		
			runOnUiThread(new Runnable() {
				public void run() {
					if (rssFeed != null) {

					}

				}
			});

		}

	}
}
