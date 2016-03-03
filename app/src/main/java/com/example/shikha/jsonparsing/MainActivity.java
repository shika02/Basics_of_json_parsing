package com.example.shikha.jsonparsing;

import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    WeaterAdapter wadapter;
    ArrayList<weather> weatherlist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listview=(ListView)findViewById(R.id.list);
        weatherlist=new ArrayList<>();



    }

    public class WeatherAsyncTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost=new HttpPost(params[0]);
                HttpResponse response=httpClient.execute(httpPost);
            int status=response.getStatusLine().getStatusCode();
                if (status==100)
                {
                    HttpEntity entity =response.getEntity();
                    String data= EntityUtils.toString(entity);

                    JSONObject jobj=new JSONObject(data);
                    JSONArray jarray =jobj.getJSONArray("weather");
                    for(int i=0;i<jarray.length();i++)
                    {
                        weather w=new weather();
                        JSONObject realobj=jarray.getJSONObject(i);
                        w.setDescription(realobj.getString("description"));
                        w.setMain(realobj.getString("main"));
                        w.setIcon(realobj.getString("icon"));
                        w.setId(realobj.getInt("id"));
                        weatherlist.add(w);

                    }

                        return true;

                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


        protected void OnPostExecute(Boolean result)
        {
            super.onPostExecute(result);
            if(result==false)
            {
                Toast.makeText(getBaseContext(), "cannot pase sorry!", Toast.LENGTH_SHORT);
            }
            else{
                WeaterAdapter wadapter=new WeaterAdapter(getApplicationContext(),R.layout.activity_weather,
                        weatherlist);
                listview.setAdapter(wadapter);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
