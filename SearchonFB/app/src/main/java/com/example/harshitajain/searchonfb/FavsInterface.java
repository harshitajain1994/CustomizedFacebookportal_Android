package com.example.harshitajain.searchonfb;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harshitajain on 4/19/17.
 */

public interface FavsInterface{
    public void saveFavorites(Context context, List<User_obj> favorites);
    public void addFavorite(Context context, User_obj User_obj);
    public void removeFavorite(Context context, User_obj User_obj);
    public ArrayList<User_obj> getFavorites(Context context);
    public String getFavoritesString(Context context);
}
