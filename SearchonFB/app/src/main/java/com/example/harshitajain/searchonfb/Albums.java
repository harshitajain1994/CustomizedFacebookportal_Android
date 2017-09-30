package com.example.harshitajain.searchonfb;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * Created by harshitajain on 4/16/17.
 */

public class Albums extends Fragment {
    String json_string=null;
    //ArrayList<String> album_name_arr= new ArrayList<String>();
    ArrayList<Album_obj> album_arr = new ArrayList<Album_obj>();
    int lastExpandedPosition;
    ExpandableListView listView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listChildData;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.albums, container, false);
        this.json_string = getArguments().getString("details"); // getting the arguments passed
        System.out.println("testing daat" + json_string+"here" + json_string.length());
        if(json_string == null || json_string == "" || json_string.length()==0){
            rootView = inflater.inflate(R.layout.noalbums, container, false);
            System.out.println("No Albums Found");
            return rootView;
        }

        lastExpandedPosition = -1;

        try {
            JSONArray json_data;
            //System.out.println("theid for details is : " + (new JSONObject(json_string).getString("id")));
            if(!(new JSONObject(json_string).has("albums"))){
                rootView = inflater.inflate(R.layout.noalbums, container, false);
                System.out.println("No Albums Found");
                return rootView;
            }
            else{
                System.out.println("theid for details is : " + (new JSONObject(json_string).getString("id")));
                listDataHeader = new ArrayList<String>() ;
                listChildData = new HashMap<String, List<String>>();
                json_data = new JSONObject(new JSONObject(json_string).getString("albums")).getJSONArray("data");
                int count = 0;
                while(count < json_data.length()) {
                    JSONObject obj = json_data.getJSONObject(count);
                    Album_obj album_obj = new Album_obj((String)obj.getString("name"));
                    listDataHeader.add((String)obj.getString("name"));

                    List<String> temp = new ArrayList<String>();
                    //album_name_arr.add((String)obj.getString("name") );

                    //pic_url_arr.add((String)new JSONObject(new JSONObject(obj.getString("picture")).getString("data")).getString("url"));
                    JSONArray pics_arr = new JSONArray();
                    //pic_url_arr.add((String)new JSONObject(new JSONObject(obj.getString("picture")).getString("data")).getString("url"));
                    if(new JSONObject(String.valueOf(obj)).has("photos")) {

                        pics_arr = new JSONObject(obj.getString("photos")).getJSONArray("data");
                    }
                    //JSONArray pics_arr = new JSONObject(obj.getString("photos")).getJSONArray("data");
                    int pic_count = 0;
                    while(pic_count < pics_arr.length()){
                        // get pics here
                        String pic_url = pics_arr.getJSONObject(pic_count).getString("picture");
                        album_obj.add_pic_to_album(pic_url);
                        temp.add(pic_url);
                        pic_count++;
                    }
                    listChildData.put((String)obj.getString("name"), temp);
                    album_arr.add(album_obj);
                    //user_adaptor.add(user_tab);
                    count++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //print_albums(album_arr);
        listView = (ExpandableListView) rootView.findViewById(R.id.expandableLV);

//
        listView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    listView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        AlbumAdaptor albumAdaptor = new AlbumAdaptor(getActivity(), listDataHeader,listChildData);
        listView.setAdapter(albumAdaptor);


        return rootView;
    }

    void print_albums(ArrayList<Album_obj> album_arr){
        for(Album_obj obj : album_arr){
            System.out.print(obj.getName() + "-> " );
            for(String s :obj.getAlbum_pic_urls()){
                System.out.print(s + ", ");
            }
            System.out.println();
        }
    }


}


