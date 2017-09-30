    package com.example.harshitajain.searchonfb;

    /**
     * Created by harshitajain on 4/14/17.
     */

    import android.content.Context;
    import android.os.Bundle;
    import android.support.v4.app.Fragment;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.EditText;
    import android.widget.ListAdapter;
    import android.widget.ListView;
    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;
    import android.widget.Button;

    import java.net.MalformedURLException;
    import java.util.ArrayList;
    import java.util.List;

    public class Users  extends Fragment {
        ListView listView;
        String json_string;
        JSONArray json_data;
        int index = 0;
        String tab;
        int size=0;
        int clickcount=0;


        ArrayList<String> id_arr;
        ArrayList<String> pic_url_arr;
        ArrayList<String> name_arr;


        ArrayList<String> tid= new ArrayList<String>();
        ArrayList<String> tname= new ArrayList<String>();
        ArrayList<String> tpic= new ArrayList<String>();

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            boolean fav_flag = false;
            View rootView = inflater.inflate(R.layout.users, container, false);
            if(getArguments().containsKey("data")){
                this.json_string = getArguments().getString("data"); // getting the arguments passed
            }
            else{
                this.json_string = getArguments().getString("data_fav"); // getting the arguments passed
                fav_flag = true;
            }

            System.out.println("in users, the format is" +this.json_string);
            this.tab=getArguments().getString("tab");
           id_arr = new ArrayList<String>();
            pic_url_arr = new ArrayList<String>();
            name_arr = new ArrayList<String>();

            try {
                System.out.println();
                if(json_string == null){
                    json_data = new JSONArray();
                }
                json_data = new JSONObject(json_string).getJSONArray("data");
                //System.out.println("checking favs data" +this.json_string);
                int count = 0;
                while(count < json_data.length()) {
                    JSONObject obj = json_data.getJSONObject(count);
                    if(fav_flag){
                        if(checkFavoriteItem(obj.getString("id"), container.getContext())){
                            id_arr.add(obj.getString("id"));
                            name_arr.add( (String)obj.getString("name"));
                            if(obj.has("pic_url")){
                                pic_url_arr.add((String)obj.getString("pic_url"));
                            }
                            else{
                                pic_url_arr.add((String)new JSONObject(new JSONObject(obj.getString("picture")).getString("data")).getString("url"));
                            }
                        }
                    }
                    else{
                        id_arr.add(obj.getString("id"));
                        name_arr.add( (String)obj.getString("name"));
                        if(obj.has("pic_url")){
                            pic_url_arr.add((String)obj.getString("pic_url"));
                        }
                        else{
                            pic_url_arr.add((String)new JSONObject(new JSONObject(obj.getString("picture")).getString("data")).getString("url"));
                        }
                    }

                    //user_adaptor.add(user_tab);
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //listView = (ListView) rootView.findViewById(R.id.list_user);

            final ListView listView =(ListView) rootView.findViewById(R.id.list_user);
            final Button prev= (Button) rootView.findViewById(R.id.prev);
            final Button next= (Button) rootView.findViewById(R.id.next);

            size=id_arr.size();

            if(size<10)
            {
                tid.clear();
                tname.clear();
                tpic.clear();
                tid.addAll(id_arr.subList(0, size));
                tname.addAll(name_arr.subList(0, size));
                tpic.addAll(pic_url_arr.subList(0, size));

            }
            else {
                tid.clear();
                tname.clear();
                tpic.clear();
                tid.addAll(id_arr.subList(0, 10));
                tname.addAll(name_arr.subList(0, 10));
                tpic.addAll(pic_url_arr.subList(0, 10));

            }
            //System.out.println("Name"+name);
            ListAdapter adapter = new UsersAdaptor(getActivity(), tid, tname, tpic,tab);
            listView.setAdapter(adapter);

            prev.setEnabled(false);
            next.setEnabled(false);

            if(id_arr.size()>10) {
                next.setEnabled(true);
            }

            next.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    // System.out.println("Clicked");
                    if(clickcount==0) {
                        clickcount++;
                        prev.setEnabled(true);
                        if(size>=20) {
                            tid.clear();
                            tname.clear();
                            tpic.clear();
                            tid.addAll(id_arr.subList(10, 20));
                            tname.addAll(name_arr.subList(10, 20));
                            tpic.addAll(pic_url_arr.subList(10, 20));
                            ListAdapter adapter = new UsersAdaptor(getActivity(), tid, tname, tpic,tab);
                            listView.setAdapter(adapter);
                        }
                        else{
                            tid.clear();
                            tname.clear();
                            tpic.clear();
                            tid.addAll(id_arr.subList(10, size));
                            tname.addAll(name_arr.subList(10, size));
                            tpic.addAll(pic_url_arr.subList(10, size));
                            ListAdapter adapter = new UsersAdaptor(getActivity(), tid, tname, tpic,tab);
                            listView.setAdapter(adapter);
                            next.setEnabled(false);

                        }
                    }
                    else if(clickcount==1)
                    {
                        clickcount++;
                        prev.setEnabled(true);
                        tid.clear();
                        tname.clear();
                        tpic.clear();
                        tid.addAll(id_arr.subList(20, size));
                        tname.addAll(name_arr.subList(20, size));
                        tpic.addAll(pic_url_arr.subList(20, size));
                        ListAdapter adapter = new UsersAdaptor(getActivity(), tid, tname, tpic,tab);
                        listView.setAdapter(adapter);
                        next.setEnabled(false);

                    }




                }

            });



            //Previous Button

            prev.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    // System.out.println("Clicked");
                    if(clickcount==0) {
                        prev.setEnabled(false);
                        next.setEnabled(true);
                    }
                    else if(clickcount==1){
                        tid.clear();
                        tname.clear();
                        tpic.clear();
                        tid.addAll(id_arr.subList(0, 10));
                        tname.addAll(name_arr.subList(0, 10));
                        tpic.addAll(pic_url_arr.subList(0, 10));
                        ListAdapter adapter = new UsersAdaptor(getActivity(), tid, tname, tpic,tab);
                        listView.setAdapter(adapter);
                        clickcount--;
                        prev.setEnabled(false);
                        next.setEnabled(true);

                    }

                    else if(clickcount==2)
                    {
                        clickcount--;
                        prev.setEnabled(true);
                        next.setEnabled(true);
                        tid.clear();
                        tname.clear();
                        tpic.clear();
                        tid.addAll(id_arr.subList(10, 20));
                        tname.addAll(name_arr.subList(10, 20));
                        tpic.addAll(pic_url_arr.subList(10, 20));
                        ListAdapter adapter = new UsersAdaptor(getActivity(), tid, tname, tpic,tab);
                        listView.setAdapter(adapter);

                    }




                }

            });

            //System.out.println(listView);
//        ListAdapter adapter = new CustomAdapter(getActivity(), id, name, pic);
//        listView.setAdapter(adapter);



            return rootView;
        }

        public boolean checkFavoriteItem(String id, Context context) {
            boolean check = false;
            FavsInterface sharedPreference ;
            switch (this.tab){
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

            List<User_obj> favorites = sharedPreference.getFavorites(context);
            if (favorites != null) {
                for (User_obj User_obj : favorites) {
                    //System.out.println(User_obj+"->"+checkUser_obj);
                    if (User_obj.getId().equals(id)) {
                        //System.out.println("Matched");
                        check = true;
                        break;
                    }
                }
            }
            return check;
        }




//        ListAdapter user_adaptor = new UsersAdaptor(getActivity(), id_arr, name_arr, pic_url_arr, this.tab);
//            listView.setAdapter(user_adaptor);
//            return rootView;
//        }


    }
