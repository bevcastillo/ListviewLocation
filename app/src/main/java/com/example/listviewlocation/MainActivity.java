package com.example.listviewlocation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;

    EditText txtsearch;

    ListView lv;

    ArrayList<Places> list = new ArrayList<Places>();
    ArrayList<Places> searchlist = new ArrayList<Places>();

    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        txtsearch = (EditText)findViewById(R.id.txtSearch);

        lv = (ListView) findViewById(R.id.listview1);
        db = new DatabaseHelper(this);
        list = db.getAll();

        adapter = new ItemAdapter(this,searchlist);
        //attach adapter to the listview
        this.lv.setAdapter(adapter);

        //put a listener to the listview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,EditDataActivity.class);
                intent.putExtra("places",list.get(position));
                startActivity(intent);
            }
        });

        this.txtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //clear the searchlist
                searchlist.clear();

                String s1 = s.toString();
                Pattern pattern = Pattern.compile(s1);

                for(int i = 0; i < list.size(); i++){
                    Matcher match = pattern.matcher(list.get(i).getName());
                    if(match.find()){
                        searchlist.add(list.get(i));
                        adapter.notifyDataSetChanged();
                    }
                }
                //update listview
                adapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflate the menu; this adds item to the actionbar if ir is present
        getMenuInflater().inflate(R.menu.locationmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if(id == R.id.addmenu){
            Intent add = new Intent(MainActivity.this,Add.class);
            this.startActivityForResult(add,0);
        }

        if (id == R.id.exportmenu) {
            Toast.makeText(this, "Upload to backend", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

//    public boolean checkNetworkConnection() {
//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        boolean isConnected = false;
//        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
//            // show "Connected" & type of network "WIFI or MOBILE"
//            Toast.makeText(this, "Connected to the internet", Toast.LENGTH_SHORT).show();
//
//        } else {
//            // show "Not Connected"
//            Toast.makeText(this, "Please check internet connectivity", Toast.LENGTH_SHORT).show();
//        }
//
//        return isConnected;
//    }


    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //check resultcode
        if(resultCode == Activity.RESULT_OK){
            Bundle b = data.getExtras();
            String name = b.getString("locname");
            double lat = Double.parseDouble(b.getString("lat"));
            double lng = Double.parseDouble(b.getString("lng"));
            //Places places = new Places(1,name,latlng);
            Places places = new Places(1,name,lat,lng);
            list.add(places);
            this.adapter.notifyDataSetChanged();

        }
    }*/

}
