package com.example.lab2jmm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
 
public class AndroidRSSReaderApplicationActivity extends Activity {
   
    private ProgressDialog pDialog;
 
  
    ArrayList<HashMap<String, String>> rssFeedList;
 
    RSSParser rssParser = new RSSParser();
 
    RSSFeed rssFeed;
 
   
    ImageButton dodadilink;
 
    
    String[] sqliteIds;
 
    public static String TAG_ID = "id";
    public static String TAG_TITLE = "title";
    public static String TAG_LINK = "link";
 
  
    ListView lv;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.link_list);
 
        dodadilink = (ImageButton) findViewById(R.id.dodadilink);
 
        
        rssFeedList = new ArrayList<HashMap<String, String>>();
 
       
        new loadStoreSites().execute();
 
       
        lv = (ListView) findViewById(R.id.list); 
 
       
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
              
                String sqlite_id = ((TextView) view.findViewById(R.id.sqlite_id)).getText().toString();
               
                Intent in = new Intent(getApplicationContext(), ListRSSItemsActivity.class);
               
                in.putExtra(TAG_ID, sqlite_id);
                startActivity(in);
            }
        });
 
       
        dodadilink.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DodadinovlinkActivity.class);
               
                startActivityForResult(i, 100);
            }
        });
    }
 
   
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == 100) {
           
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
 
   
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
        ContextMenuInfo menuInfo) {
      if (v.getId()==R.id.list) {
        menu.setHeaderTitle("Delete");
            menu.add(Menu.NONE, 0, 0, "Избриши линк");
      }
    }
 
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
      AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
      int menuItemIndex = item.getItemId();
     
      if(menuItemIndex == 0){
         
          RSSDatabaseHandler rssDb = new RSSDatabaseHandler(getApplicationContext());
          Website site = new Website();
          site.setId(Integer.parseInt(sqliteIds[info.position]));
          rssDb.deleteSite(site);
          
          Intent intent = getIntent();
          finish();
          startActivity(intent);
      }
 
      return true;
    }
 
    
    class loadStoreSites extends AsyncTask<String, String, String> {
 
       
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(
                    AndroidRSSReaderApplicationActivity.this);
            pDialog.setMessage("вчитување на линкови ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        
        @Override
        protected String doInBackground(String... args) {
           
            runOnUiThread(new Runnable() {
                public void run() {
                    RSSDatabaseHandler rssDb = new RSSDatabaseHandler(
                            getApplicationContext());
 
                  
                    List<Website> siteList = rssDb.getAllSites();
 
                    sqliteIds = new String[siteList.size()];
 
                   
                    for (int i = 0; i < siteList.size(); i++) {
 
                        Website s = siteList.get(i);
 
                        
                        HashMap<String, String> map = new HashMap<String, String>();
 
                       
                        map.put(TAG_ID, s.getId().toString());
                        map.put(TAG_TITLE, s.getTitle());
                        map.put(TAG_LINK, s.getLink());
 
                        
                        rssFeedList.add(map);
 
                       
                        sqliteIds[i] = s.getId().toString();
                    }
                   
                    ListAdapter adapter = new SimpleAdapter(
                            AndroidRSSReaderApplicationActivity.this,
                            rssFeedList, R.layout.link_list_row,
                            new String[] { TAG_ID, TAG_TITLE, TAG_LINK },
                            new int[] { R.id.sqlite_id, R.id.title, R.id.link });
                   
                    lv.setAdapter(adapter);
                    registerForContextMenu(lv);
                }
            });
            return null;
        }
 
        
        protected void onPostExecute(String args) {
           
            pDialog.dismiss();
        }
 
    }
}