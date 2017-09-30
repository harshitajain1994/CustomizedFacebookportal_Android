package com.example.harshitajain.searchonfb;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class Results extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static Context context;
    String str_arr[];
    public static int forResume = 0;
    //Bundle savedInstanceState1;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //mViewPager = (ViewPager) findViewById(R.id.container);
        if(mViewPager!=null){
           mViewPager.setAdapter(mSectionsPagerAdapter);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);
        // setting icons for tabs
        tabLayout.getTabAt(0).setIcon(R.drawable.users);
        tabLayout.getTabAt(1).setIcon(R.drawable.pages);
        tabLayout.getTabAt(2).setIcon(R.drawable.events);
        tabLayout.getTabAt(3).setIcon(R.drawable.places);
        tabLayout.getTabAt(4).setIcon(R.drawable.groups);

        // mSectionsPagerAdapter.getItem(0);
        System.out.println("complete");

        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        forResume = 0;
        //savedInstanceState1 = savedInstanceState;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_results);
        context= getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        if(mViewPager!=null){
            mViewPager.setAdapter(mSectionsPagerAdapter);
        }



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        // setting icons for tabs
        tabLayout.getTabAt(0).setIcon(R.drawable.users);
        tabLayout.getTabAt(1).setIcon(R.drawable.pages);
        tabLayout.getTabAt(2).setIcon(R.drawable.events);
        tabLayout.getTabAt(3).setIcon(R.drawable.places);
        tabLayout.getTabAt(4).setIcon(R.drawable.groups);

    }

   // @Override
//    public void onResume(){
//        forResume++;
//        //super.onResume();
//        // put your code here...
//        //setContentView(R.layout.activity_results);
//        //this.recreate();
//
//        if(forResume > 1){
//            System.out.println("here on resume" + forResume);
//            this.recreate();
//        }
//        super.onResume();
//    }

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
        else if(id==android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getDetails(View view) throws MalformedURLException {
        //Toast.makeText(MainActivity.this,"im here",Toast.LENGTH_SHORT).show();
        //System.out.println("clicked" + 0 + view.getTag().toString());
        str_arr = view.getTag().toString().split(", ; ,");
        //System.out.println("broen is " + str_arr[1] + "   " + str_arr[2]);
        String url = new String("http://sample-env-1.x9nn55ifbz.us-west-2.elasticbeanstalk.com/index.php/index.php?id=" + str_arr[0]);
        DeatilsAsyncTask json_async_details = new DeatilsAsyncTask();
        json_async_details.execute(url);
        //return;

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // returning the current tabs
            switch (position) {
                case 0:
                    Users user_tab = new Users();
                    Bundle args = new Bundle();
                    String json_string = null;
                    if(getIntent().hasExtra("json_string_user")){
                        json_string = getIntent().getExtras().getString("json_string_user");
                        args.putString("data", json_string);

                    }
                    else {
                        setTitle("Favorites");
                        json_string = getIntent().getExtras().getString("fav_user");
                        System.out.println("this is fav data : " + json_string);
                        args.putString("data_fav", json_string);
                    }


                    args.putString("tab","users");
                    user_tab.setArguments(args);
                    return user_tab;

                case 1:
                    Users page_tab = new Users();
                    Bundle args1 = new Bundle();
                    String json_string1 = null;
                    if(getIntent().hasExtra("json_string_page")){
                        json_string1 = getIntent().getExtras().getString("json_string_page");
                        args1.putString("data", json_string1);
                    }
                    else {
                        json_string1 = getIntent().getExtras().getString("fav_page");
                        args1.putString("data_fav", json_string1);
                    }


                    args1.putString("tab","pages");
                    page_tab.setArguments(args1);
                    return page_tab;

                case 2:
                    Users event_tab = new Users();
                    Bundle args2 = new Bundle();
                    String json_string2 = null;
                    if(getIntent().hasExtra("json_string_event")){
                        json_string2 = getIntent().getExtras().getString("json_string_event");
                        args2.putString("data", json_string2);
                    }
                    else {
                        json_string2 = getIntent().getExtras().getString("fav_event");
                        args2.putString("data_fav", json_string2);
                    }


                    args2.putString("tab","events");
                    event_tab.setArguments(args2);
                    return event_tab;

                case 3:
                    Users place_tab = new Users();
                    Bundle args3 = new Bundle();
                    String json_string3 = null;
                    if(getIntent().hasExtra("json_string_place")){
                        json_string3 = getIntent().getExtras().getString("json_string_place");
                        args3.putString("data", json_string3);
                    }
                    else {
                        json_string3 = getIntent().getExtras().getString("fav_place");
                        args3.putString("data_fav", json_string3);
                    }


                    args3.putString("tab","places");
                    place_tab.setArguments(args3);
                    return place_tab;

                case 4:
                    Users group_tab = new Users();
                    Bundle args4 = new Bundle();
                    String json_string4 = null;
                    if(getIntent().hasExtra("json_string_group")){
                        json_string4 = getIntent().getExtras().getString("json_string_group");
                        args4.putString("data", json_string4);
                    }
                    else {
                        json_string4 = getIntent().getExtras().getString("fav_group");
                        args4.putString("data_fav", json_string4);
                    }


                    args4.putString("tab","groups");
                    group_tab.setArguments(args4);
                    return group_tab;

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Users";
                case 1:
                    return "Pages";
                case 2:
                    return "Event";
                case 3:
                    return "Places";
                case 4:
                    return "Groups";
            }
            return null;
        }
    }

    class DeatilsAsyncTask extends AsyncTask<Object, Object, Void> {

        String details_string= null;
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(details_string == null){
                //Toast.makeText(MainActivity.this,"No data Found",Toast.LENGTH_SHORT).show();
                //System.out.println("No data found");
            }
            else{
                Intent intent;
                intent = new Intent( Results.context, Details.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("details_string", details_string);
                intent.putExtra("name", str_arr[1]);
                intent.putExtra("profile_pic_url", str_arr[2]);
                intent.putExtra("user_id", str_arr[0]);
                intent.putExtra("tab_name", str_arr[3]);
                Results.context.startActivity(intent);
            }

        }


        @Override
        protected Void doInBackground(Object... urls) {

            JSONObject response = null;
            //------------------>>
            String details_url = (String) urls[0];
            details_string = getdataURL(details_url);
            return null;
        }

         String getdataURL(String url1){
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



}
