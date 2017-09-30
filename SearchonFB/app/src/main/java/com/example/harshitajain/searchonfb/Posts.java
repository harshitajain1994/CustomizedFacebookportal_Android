package com.example.harshitajain.searchonfb;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by harshitajain on 4/16/17.
 */

public class Posts extends Fragment {
    String json_string=null;
    String user_name = null;
    String user_pic = null;
    ArrayList<Post_obj> posts_arr = new ArrayList<Post_obj>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.posts, container, false);
        this.json_string = getArguments().getString("details"); // getting the arguments passed
        this.user_name = getArguments().getString("name");
        this.user_pic = getArguments().getString("profile_pic_url");
        ListView listView;
        if( json_string == null || json_string == "" || json_string.length()==0){
            rootView = inflater.inflate(R.layout.noposts, container, false);
            //System.out.println("No Albums Found");
            return rootView;
        }

        try {
            JSONArray json_data;
            if(!(new JSONObject(json_string).has("posts"))){
                rootView = inflater.inflate(R.layout.noposts, container, false);
                //System.out.println("No Albums Found");
                return rootView;
            }
            else{
                json_data = new JSONObject(new JSONObject(json_string).getString("posts")).getJSONArray("data");
                int count = 0;
                while(count < json_data.length()) {
                    JSONObject obj = json_data.getJSONObject(count);
                    if(obj!=null){
                        Post_obj post_obj ;
                        if(obj.has("message")){
                             post_obj = new Post_obj((String)obj.getString("message"));
                        }
                        else{
                            post_obj = new Post_obj(" ");
                        }
                        post_obj.add_created_time((String)obj.getString("created_time"));
                        posts_arr.add(post_obj);
                    }

                    //user_adaptor.add(user_tab);
                    count++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        print_posts(posts_arr);
        listView = (ListView) rootView.findViewById(R.id.post_arr);
        //String test_arr[] = {"a","b"};
        ListAdapter post_adaptor = new PostAdaptor(getActivity(), posts_arr, user_name, user_pic);
        //ListAdapter post_adaptor =new ArrayAdapter(getActivity(), R.layout.row_layout_posts,test_arr);
        listView.setAdapter(post_adaptor);

        return rootView;
    }

    void print_posts(ArrayList<Post_obj> posts_arr){
        for(Post_obj obj : posts_arr){
            System.out.println(obj.getMsg() + " ->" +obj.getCreated_time());
        }
    }
}

