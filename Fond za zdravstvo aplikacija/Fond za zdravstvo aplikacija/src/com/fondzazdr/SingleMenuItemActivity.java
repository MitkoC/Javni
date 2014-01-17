package com.fondzazdr;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fondzazdr.MainActivity.MyTask;
import com.samir.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ViewFlipper;

public class SingleMenuItemActivity extends Activity implements
		OnItemClickListener {

	private static final String OB_rezultati = "results";
	private static final String LISTA_rezultati = "bindings";
	private static final String ID_OBJEKT = "ID";
	private static final String ID_vrednosts = "value";
	private static final String OBJEKT_NAZIVNALEK = "NazivnaLek";
	private static final String NAZIVNALEK_vrednost = "value";

	private static final String OBJEKT_Proizvoditel = "Proizveduvac";
	private static final String Proizvoditel_vrednost = "value";

	private static final String OBJEKT_Cena = "Cenasoddv";
	private static final String CENA_vrednost = "value";

	private static final String OBJEKT_slicni = "slicni";
	private static final String Slicni_vrednost = "value";

	private static final String OBJEKT_slicniid = "idslicni";
	private static final String ID_vrednost = "value";

	private static final String OBJEKT_Description = "Description";
	private static final String DescriptionValue = "value";

	private static final String OBJEKT_FoodInteraction = "FoodInteractions";
	private static final String FoodInteractionsvalue = "value";

	private static final String OBJEKT_Indications = "Indications";
	private static final String Indicationssvalue = "value";

	slicnilekovilista objAdapter;
	List<Item> arrayOfList;
	ListView listView;
	ViewFlipper flipper;

	private ListView monthsListView;

	private ArrayAdapter arrayAdapter;

	// /za page switcherot
	private Animation inFromRightAnimation() {

		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(500);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}

	private Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(500);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	private Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(500);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	private Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(500);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.itemselectintent);

		listView = (ListView) findViewById(R.id.slicnilista);
		listView.setOnItemClickListener(this);
		arrayOfList = new ArrayList<Item>();
		// getting intent data
		Intent in = getIntent();

		// zemi id na lekot za prikaz na detali
		String id = in.getStringExtra(ID_vrednost);

		// inicijalizacija na kopcinjata za switch view
		flipper = (ViewFlipper) findViewById(R.id.flipper);
	    Button button1 = (Button) findViewById(R.id.But);
		Button button2 = (Button) findViewById(R.id.Button02);
		button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				flipper.setInAnimation(inFromRightAnimation());
				flipper.setOutAnimation(outToLeftAnimation());
				flipper.showNext();
			}
		});

		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				flipper.setInAnimation(inFromLeftAnimation());
				flipper.setOutAnimation(outToRightAnimation());
				flipper.showPrevious();
			}
		});

		String query2 = "http://linkeddata.finki.ukim.mk/sparql?default-graph-uri=&query=PREFIX+d%3A+%3Chttp%3A%2F%2Fwifo5-04.informatik.uni-mannheim.de%2Fdrugbank%2Fresource%2Fdrugbank%2F%3E%0D%0APREFIX+hifm%3A+%3Chttp%3A%2F%2Fwww.fzo.org.mk%2Fontology%2Fhifm%23%3E%0D%0APREFIX+drugbank%3A+%3Chttp%3A%2F%2Fwifo5-04.informatik.uni-mannheim.de%2Fdrugbank%2Fresource%2Fdrugbank%2F%3E+%0D%0ASELECT+DISTINCT+%3FID+%3Fgenerickoime+%3FNazivnaLek+%3FProizveduvac+%3FCenasoddv+%3Fidslicni+%3Fslicni+%3Fcenaslicni+%3Fproizvoditelslicni+%3FDescription+%3FFoodInteractions+%3FIndications%0D%0AWHERE++%0D%0A%7B%0D%0Ahifm%3A"
				+ id
				+ "+hifm%3Aid+%3FID%3B%0D%0A+++d%3AgenericName+%3Fgenerickoime%3B%0D%0A+++d%3AbrandName+%3FNazivnaLek%3B%0D%0A+++hifm%3Amanufacturer+%3FProizveduvac%3B%0D%0A+++hifm%3ArefPriceWithVAT+%3FCenasoddv%3B%0D%0A+++hifm%3AsimilarTo++%3Fsl.%0D%0A++%3Fsl+hifm%3Aid+%3Fidslicni%3B%0D%0A++++d%3AbrandName+%3Fslicni%3B%0D%0A+++%0D%0A++++hifm%3ArefPriceWithVAT+%3Fcenaslicni%3B%0D%0A++++hifm%3Amanufacturer+%3Fproizvoditelslicni.%0D%0A++++++%0D%0Ahifm%3A"
				+ id
				+ "+owl%3AseeAlso+%3Fdbd+%0D%0A+SERVICE+%3Chttp%3A%2F%2Fwifo5-04.informatik.uni-mannheim.de%2Fdrugbank%2Fsparql%3E+%0D%0A+%7B+%0D%0A+%3Fdbd+drugbank%3Adescription+%3FDescription%3B%0D%0A+drugbank%3Aindication++%3FIndications%3B%0D%0A+drugbank%3AfoodInteraction+%3FFoodInteractions.%0D%0A+%7D+%0D%0A+%0D%0A+++%0D%0A%0D%0A%0D%0A++%0D%0A+++%0D%0A%7D%0D%0AORDER+BY%28%3Fcenaslicni%29%0D%0A&should-sponge=&format=json";

		String query2a = "http://linkeddata.finki.ukim.mk/sparql?default-graph-uri=http%3A%2F%2Fwww.fzo.org.mk%2Fontology%2Fhifm%23&should-sponge=grab-all&query=PREFIX+d%3A+%3Chttp%3A%2F%2Fwifo5-04.informatik.uni-mannheim.de%2Fdrugbank%2Fresource%2Fdrugbank%2F%3E%0D%0APREFIX+hifm%3A+%3Chttp%3A%2F%2Fwww.fzo.org.mk%2Fontology%2Fhifm%23%3E%0D%0ASELECT+DISTINCT+%3FID++%3FNazivnaLek+%3FProizveduvac+%3FCenasoddv+%3Fidslicni+%3Fslicni+%3Fcenaslicni+%3Fproizvoditelslicni%0D%0AWHERE++{hifm:"
				+ id
				+ "+hifm%3Aid+%3FID%3B%0D%0A+++d%3AbrandName+%3FNazivnaLek%3B%0D%0A+++hifm%3Amanufacturer+%3FProizveduvac%3B%0D%0A+++hifm%3ArefPriceWithVAT+%3FCenasoddv%3B%0D%0A+++hifm%3AsimilarTo++%3Fsl.%0D%0A++%3Fsl+hifm%3Aid+%3Fidslicni%3B%0D%0A++++d%3AbrandName+%3Fslicni%3B%0D%0A++++hifm%3ArefPriceWithVAT+%3Fcenaslicni%3B%0D%0A++++hifm%3Amanufacturer+%3Fproizvoditelslicni.%0D%0A++++++%0D%0A%0D%0A+%0D%0A+++%0D%0A%0D%0A%0D%0A++%0D%0A+++%0D%0A%7D%0D%0AORDER+BY%28%3Fcenaslicni%29%0D%0A&debug=on&timeout=&format=JSON";

		String m = " http://linkeddata.finki.ukim.mk/sparql?default-graph-uri=&should-sponge=&query=%0D%0APREFIX+d%3A+%3Chttp%3A%2F%2Fwifo5-04.informatik.uni-mannheim.de%2Fdrugbank%2Fresource%2Fdrugbank%2F%3E%0D%0APREFIX+hifm%3A+%3Chttp%3A%2F%2Fwww.fzo.org.mk%2Fontology%2Fhifm%23%3E%0D%0ASELECT+DISTINCT+%3FID++%3FNazivnaLek+%3FProizveduvac+%3FCenasoddv+%3Fslicni%0D%0AWHERE++{hifm:"
				+ id
				+ "+hifm%3Aid+%3FID%3B%0D%0A+++d%3AbrandName+%3FNazivnaLek%3B%0D%0A+++hifm%3Amanufacturer+%3FProizveduvac%3B%0D%0A+++hifm%3ArefPriceWithVAT+%3FCenasoddv%3B%0D%0A+++hifm%3AsimilarTo+%3Fz.%0D%0A%3Fz+d%3AbrandName+%3Fslicni.%0D%0A+++%0D%0A%0D%0A%0D%0A++%0D%0A+++%0D%0A%7D%0D%0Alimit+100&debug=on&timeout=&format=JSON";
		if (Utils.isNetworkAvailable(SingleMenuItemActivity.this)) {
			new MyTask().execute(query2);
		} else {
			showToast("No Internet Connection!!!");
		}

	}

	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(SingleMenuItemActivity.this);
			pDialog.setMessage("Reading Medicine Details...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			return Utils.getJSONString(params[0]);
		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == result || result.length() == 0) {
				showToast("no data!!!");
				SingleMenuItemActivity.this.finish();
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
						String vrednostid = id.getString(ID_vrednosts);
						objItem.setid(vrednostid);

						// zemi ime na lekot
						JSONObject imelek = objJson
								.getJSONObject(OBJEKT_NAZIVNALEK);
						String vrednostlek = imelek
								.getString(NAZIVNALEK_vrednost);
						objItem.setnaziv(vrednostlek);
						TextView ime = (TextView) findViewById(R.id.headertextlek);
						ime.setText(Html.fromHtml(objItem.getnaziv()));

						// zemi proizveduvac queryto vraka eden rezultat za
						// proizveduvac
						JSONObject pro = objJson
								.getJSONObject(OBJEKT_Proizvoditel);
						String vrednostpro = pro
								.getString(Proizvoditel_vrednost);
						objItem.setproizvoditel(vrednostpro);
						TextView p = (TextView) findViewById(R.id.proizvoditel);
						p.setText(Html.fromHtml(objItem.getproizvoditel()));

						// zemi cena queryto vraka eden rezultat za cena
						JSONObject cenalek = objJson.getJSONObject(OBJEKT_Cena);
						String cenavrednost = cenalek.getString(CENA_vrednost);
						objItem.setcena(cenavrednost);
						TextView c = (TextView) findViewById(R.id.cena);
						c.setText(Html.fromHtml(objItem.getcena()) + " mkd");

						JSONObject FoodInteractions = objJson
								.getJSONObject(OBJEKT_FoodInteraction);
						String FoodInteractionsvrednost = FoodInteractions
								.getString(FoodInteractionsvalue);
						objItem.setfoodinteractions(FoodInteractionsvrednost);
						TextView f = (TextView) findViewById(R.id.FoodInteractions);
						f.setText(Html.fromHtml(objItem.getfoodinteractions()));
                        
						Button check = (Button) findViewById(R.id.But);
					/*	if(FoodInteractionsvrednost!=null && !FoodInteractionsvrednost.isEmpty())
						{   
							showToast("dasdas!!!");
							check.setVisibility(View.INVISIBLE);						
						}*/
						JSONObject Indications = objJson
								.getJSONObject(OBJEKT_Indications);
						String Indicationssvrednost = Indications
								.getString(Indicationssvalue);
						objItem.setIndications(Indicationssvrednost);
						TextView ind = (TextView) findViewById(R.id.Indications);
						ind.setText(Html.fromHtml(objItem.getIndications()));

						JSONObject Description = objJson
								.getJSONObject(OBJEKT_Description);
						String Descriptionvrednost = Description
								.getString(DescriptionValue);
						objItem.setDescription(Descriptionvrednost);
						TextView des = (TextView) findViewById(R.id.Description);
						des.setText(Html.fromHtml(objItem.getDescription()));

						JSONObject idslicni = objJson
								.getJSONObject(OBJEKT_slicniid);
						String vrednostidslicni = idslicni
								.getString(ID_vrednost);
						objItem.setslicniid(vrednostidslicni);

						JSONObject slicni = objJson
								.getJSONObject(OBJEKT_slicni);
						String slicnivrednost = slicni
								.getString(Slicni_vrednost);
						objItem.setslicni(slicnivrednost);
						// TextView slicniv = (TextView)
						// findViewById(R.id.slicni);
						// slicniv.setText(Html.fromHtml(objItem.getslicni()));

						arrayOfList.add(objItem);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				setAdapterToListview();

			}

		}

	}

	public void setAdapterToListview() {
		objAdapter = new slicnilekovilista(SingleMenuItemActivity.this,
				R.layout.slicnilekovilayout, arrayOfList);
		// View headerslicni =
		// (View)getLayoutInflater().inflate(R.layout.slicniheader, null);
		// listView.addHeaderView(headerslicni);
		listView.setAdapter(objAdapter);

	}

	public void showToast(String msg) {
		Toast.makeText(SingleMenuItemActivity.this, msg, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		finish();
		// String name = ((TextView)
		// view.findViewById(R.id.nazivnalekot)).getText().toString();
		String idlekslicni = ((TextView) view.findViewById(R.id.idleks))
				.getText().toString();
		Intent in = new Intent(getApplicationContext(),
				SingleMenuItemActivity.class);
		// in.putExtra(NAZIVNALEK_vrednost, name);
		in.putExtra(ID_vrednost, idlekslicni);

		overridePendingTransition(0, 0);
		in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		overridePendingTransition(0, 0);
		startActivity(in);
	}

}
