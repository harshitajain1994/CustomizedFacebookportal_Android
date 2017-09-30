package com.example.harshitajain.searchonfb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import java.net.*;
import java.io.*;
import org.json.*;
import android.os.*;
import android.location.LocationListener;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import static com.example.harshitajain.searchonfb.MainActivity.lat;
import static com.example.harshitajain.searchonfb.MainActivity.longi;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public static Context context;
    LocationManager mLocationManager;
    LocationListener mLocationListener;
//    static String lat = "34.0215386";
//    static String longi = "-118.2830843";
    static double lat = 37.422;
    static double longi = -122.084;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context= getApplicationContext();



        // getting keyword
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // adding
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                System.out.println("printing location " + location.getLatitude());
                lat = location.getLatitude();
                longi = location.getLongitude();
                System.out.println(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET}, 10);

            return;
        }
        else {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
                } else {
                }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_favorite) {
            //call the fvs here
           SharedPreference sharedPreference = new SharedPreference();
            SharedPreferencePages sharedPreferencepage = new SharedPreferencePages();
            SharedPreferenceEvents sharedPreferenceevent = new SharedPreferenceEvents();
            SharedPreferencePlaces sharedPreferenceplace = new SharedPreferencePlaces();
            SharedPreferenceGroups sharedPreferencegroup = new SharedPreferenceGroups();
           String fav_user, fav_place, fav_event, fav_group, fav_page;

           String json = sharedPreference.getFavoritesString(this);
            String json_page = sharedPreferencepage.getFavoritesString(this);
            String json_event = sharedPreferenceevent.getFavoritesString(this);
            String json_group = sharedPreferencegroup.getFavoritesString(this);
            String json_place = sharedPreferenceplace.getFavoritesString(this);
            System.out.println("this is the json users" + json);
            System.out.println("this is the json gropus" + json_group);
            fav_event = json_event;
            fav_group = json_group;
            fav_page = json_page;
            fav_place = json_place;
            fav_user = json;
            Intent intent;
            intent = new Intent(MainActivity.context, Results.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           intent.putExtra("fav_user", fav_user);
            intent.putExtra("fav_place", fav_place);
            intent.putExtra("fav_event", fav_event);
            intent.putExtra("fav_group", fav_group);
            intent.putExtra("fav_page", fav_page);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MainActivity.context.startActivity(intent);

        } else if (id == R.id.nav_about_me) {
            Intent intent = new Intent(MainActivity.context, AboutMe.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MainActivity.context.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void searchButton(View view) throws MalformedURLException {
        //Toast.makeText(MainActivity.this,"im here",Toast.LENGTH_SHORT).show();

        String response = "hi";
        final EditText keyword1 =  (EditText) findViewById(R.id.editText);
        String keyword =  keyword1.getText().toString();
        if(keyword.matches("")){
            Toast.makeText(MainActivity.this,"Please enter a keyword!",Toast.LENGTH_SHORT).show();

        }else {

            keyword = keyword.replaceAll("\\s+","+");
            System.out.println("key" + keyword);
            String url = new String("http://sample-env-1.x9nn55ifbz.us-west-2.elasticbeanstalk.com/index.php/index.php?keyword=" + keyword + "&choice=");
            JSONAsyncTask json_async = new JSONAsyncTask();
            json_async.execute(url);
        }
    }

    public void clearText(View view){
        System.out.println("here in clear");
        EditText edit   = (EditText)findViewById(R.id.editText);
        edit.setText("");
    }
}



class JSONAsyncTask extends AsyncTask<Object, Object, Void> {

    public String result_user = null;
    public String result_page = null;
    public String result_place = null;
    public String result_event = null;
    public String result_group = null;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(result_user == null){
            //Toast.makeText(MainActivity.this,"No data Found",Toast.LENGTH_SHORT).show();
            System.out.println("No data found");
        }
        else{
            Intent intent;
            intent = new Intent(MainActivity.context, Results.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("json_string_user", result_user);
            intent.putExtra("json_string_place", result_place);
            intent.putExtra("json_string_event", result_event);
            intent.putExtra("json_string_group", result_group);
            intent.putExtra("json_string_page", result_page);
            MainActivity.context.startActivity(intent);
        }

    }


    @Override
    protected Void doInBackground(Object... urls) {

        JSONObject response = null;

            //------------------>>
            String user_url = (String) urls[0] + "user";
            String page_url = (String) urls[0] + "page";
            String place_url = (String) urls[0] + "place&lat="+lat+"&long="+longi;
            System.out.println("url" + place_url);
            String group_url = (String) urls[0] + "group";
            String event_url = (String) urls[0] + "event";
            result_user = getdataURL(user_url);
            result_event = getdataURL(event_url);
            result_page = getdataURL(page_url);
            result_place = getdataURL(place_url);
            result_group = getdataURL(group_url);

//            System.out.println("thi is my data" + result_user);
//        System.out.println("thi is my data" + result_event);
//        System.out.println("thi is my data" + result_page);
//        System.out.println("thi is my data" + result_place);
//        System.out.println("thi is my data" + result_group);


        return null;
    }

    static String getdataURL(String url1){
        String result = null;

        try {
        URL url = new URL(url1);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        String line;
        StringBuilder sb= new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line=br.readLine()) != null) {
            sb.append(line);
        }
        result = sb.toString();

        br.close();
    } catch (ProtocolException e2) {
        e2.printStackTrace();
    } catch (IOException e2) {
        e2.printStackTrace();
    }

        return result;
    }

}
