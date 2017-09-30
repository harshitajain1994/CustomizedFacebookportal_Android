package com.example.harshitajain.searchonfb;

import android.content.Context;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import android.widget.TextView;
import android.widget.Toast;

public class Details extends AppCompatActivity {
    String details_result = null;
    FavsInterface sharedPreference;
    User_obj user_obj;
    Menu menu;
    String tab_name;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    boolean post_flag;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //adaptor of results
//        Results result = new Results();
//        //View new_view = new View(R.layout.activity_results);
//
//        result.getDetails();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         tab_name = getIntent().getExtras().getString("tab_name");
        switch (tab_name){
            case "users":
                sharedPreference = new SharedPreference();
                break;
            case "pages":
                sharedPreference = new SharedPreferencePages();
                break;
            case "groups":
                sharedPreference = new SharedPreferenceGroups();
                break;
            case "events":
                sharedPreference = new SharedPreferenceEvents();
                break;
            case "places":
                sharedPreference = new SharedPreferencePlaces();
                break;
            default:
                sharedPreference = new SharedPreference();
                break;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.albums);
        tabLayout.getTabAt(1).setIcon(R.drawable.posts);
        user_obj = new User_obj(getIntent().getExtras().getString("user_id"), getIntent().getExtras().getString("name"),getIntent().getExtras().getString("profile_pic_url"));

        details_result = getIntent().getExtras().getString("details_string");
        //System.out.println(details_result + "in tabs2");
//        MenuItem menuItem = menu.findItem(R.id.action_fav);
//        if (checkFavoriteItem(user_obj)) {
//            // display remove
//            menuItem.setTitle("Remove from Favorites");
////            holder.favoriteImg.setImageResource(R.drawable.heart_red);
////            holder.favoriteImg.setTag("red");
//        } else {
//            menuItem.setTitle("Add to Favorites");
//        }

    }

    public boolean checkFavoriteItem(User_obj checkUser_obj) {
        boolean check = false;
        List<User_obj> favorites = sharedPreference.getFavorites(this);
        if (favorites != null) {
            for (User_obj User_obj : favorites) {
                //System.out.println(User_obj+"->"+checkUser_obj);
                if (User_obj.equals(checkUser_obj)) {
                    //System.out.println("Matched");
                    check = true;
                    break;
                }
            }
        }
        return check;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        this.menu=menu;
        MenuItem menuItem = menu.findItem(R.id.action_fav);
        if (checkFavoriteItem(user_obj)) {
            // display remove
            //System.out.println("Remove");
            menuItem.setTitle("Remove from Favorites");
            // set icon to colored start
//
        } else {
            menuItem.setTitle("Add to Favorites");
            // set icoto blank star
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_fav) {
            // change the text here after checking for favs
            // create the obj\
//            String user_name = ;
//            String user_id = ;
//            String user_pic = ;


            if (checkFavoriteItem(user_obj)) {
                //System.out.println("removing from fav");
                item.setTitle("Add to Favorites");
                //System.out.println("title" + item.getTitle());
                sharedPreference.removeFavorite(this, user_obj);
                Toast.makeText(Details.this,"Removed from favorites!",Toast.LENGTH_SHORT).show();

                ///

            } else {
                //System.out.println("adding to fav");
                item.setTitle("Remove from Favorites");
                //System.out.println("title" + item.getTitle());
                String tab_name = getIntent().getExtras().getString("tab_name");
                sharedPreference.addFavorite(this, user_obj);
                Toast.makeText(Details.this,"Added to favorites!",Toast.LENGTH_SHORT).show();

            }

            return true;
        }

//
//        if (id == R.id.action_share) {
//            //System.out.println("in details share");
//            String user_pic = getIntent().getExtras().getString("profile_pic_url");
//            String user_name = getIntent().getExtras().getString("name");
//            System.out.println();
//            System.out.println("pic for fb" + user_pic);
//            String shared_toast = "Sharing " + user_name + "!!";
//            Toast.makeText(Details.this,shared_toast,Toast.LENGTH_SHORT).show();
//
//            //System.out.println("here is the url : " + user_pic);
////            facebookSDKInitialize();
////            shareDialog = new ShareDialog(this);
////            if (shareDialog.canShow(ShareLinkContent.class)) {
////                ShareLinkContent linkContent = new ShareLinkContent.Builder()
////                        .setContentTitle(user_name)
////                        .setImageUrl((Uri.parse(user_pic)))
////                        .setContentDescription("FB Search from USC CSCI571..")
////                        //.setContentUrl(Uri.parse("http://sample-env-1.x9nn55ifbz.us-west-2.elasticbeanstalk.com/index.php/index.html"))
////                        .build();
////                shareDialog.show(linkContent);
////            }
////            Toast.makeText(Details.this,"You shared this post",Toast.LENGTH_SHORT).show();
//
//
////            Toast.makeText(this, shared_toast, Toast.LENGTH_SHORT).show();
//            FacebookSdk.sdkInitialize(getApplicationContext());
//            callbackManager = CallbackManager.Factory.create();
//            shareDialog = new ShareDialog(this);
//            if (shareDialog.canShow(ShareLinkContent.class)) {
//                ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                        .setContentTitle(user_name)
//                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
//                        .setImageUrl(Uri.parse(user_pic))
//                        .setContentDescription("FB SEARCH FROM USC CSCI571...")
//                        .build();
//                shareDialog.show(linkContent);
//            }
//            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
//                @Override
//                public void onSuccess(Sharer.Result result) {
//
//                    Toast.makeText(getApplicationContext(), "You shared this post", Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onCancel() {
//                    Toast.makeText(getApplicationContext(), "Post sharing cancelled", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onError(FacebookException error) {
//
//                }
//            });
//            return true;
////            return true;
//        }


        if (id == R.id.action_share) {
              post_flag = false;
            String user_pic = getIntent().getExtras().getString("profile_pic_url");
           String user_name = getIntent().getExtras().getString("name");
            Toast.makeText(this, "Sharing "+ user_name, Toast.LENGTH_SHORT).show();
            FacebookSdk.sdkInitialize(getApplicationContext());
            callbackManager = CallbackManager.Factory.create();
            shareDialog = new ShareDialog(this);
            if (shareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(user_name)
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        .setImageUrl(Uri.parse(user_pic))
                        .setContentDescription("FB SEARCH FROM USC CSCI571...")
                        .build();
                shareDialog.show(linkContent);
            }
            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    Toast.makeText(getApplicationContext(), "You shared this post", Toast.LENGTH_LONG).show();
                    System.out.println("here" );
                    post_flag = true;

                }

                @Override
                public void onCancel() {

                    Toast.makeText(getApplicationContext(), "Post sharing cancelled", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(FacebookException error) {

                }
            });
//            if(post_flag == false){
//                Toast.makeText(getApplicationContext(), "Post sharing cancelled", Toast.LENGTH_SHORT).show();
//            }
            return true;
        }

        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    private void facebookSDKInitialize() {
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        callbackManager = CallbackManager.Factory.create();
//    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    //Albums album = new Albums();
//                    Posts post = new Posts();
//                    return post;
                    Albums album = new Albums();
                    String json_string = getIntent().getExtras().getString("details_string");
                    Bundle args = new Bundle();
                    args.putString("details", json_string);
                    album.setArguments(args);
                    return album;

                case 1:
                    Posts post = new Posts();
                    String json_string_post = getIntent().getExtras().getString("details_string");
                    String user_name = getIntent().getExtras().getString("name");
                    String user_pic = getIntent().getExtras().getString("profile_pic_url");
                    Bundle args1 = new Bundle();
                    args1.putString("details", json_string_post);
                    args1.putString("name", user_name);
                    args1.putString("profile_pic_url", user_pic);
                    post.setArguments(args1);
                    return post;
//                    Users user = new Users();
//                    return user;

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Albums";
                case 1:
                    return "Posts";
            }
            return null;
        }
    }
}
