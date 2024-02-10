package com.cwtstudio.notesappwithsp.Managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.cwtstudio.notesappwithsp.Models.Notes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SPManager {
    private static final String PREF_NAME = "My_Notes";
    private static final String LIST_KEY = "NOTES_LIST";

    public static void saveNotesList(Context context, List<Notes> notes) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String jsonList = gson.toJson(notes);
        editor.putString(LIST_KEY, jsonList);
        editor.apply();

    }

    public static List<Notes> getNotesList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonList = prefs.getString(LIST_KEY,null);
        if (jsonList != null) {
            Type type = new TypeToken<List<Notes>>() {}.getType();
            return gson.fromJson(jsonList,type);
        }
        else {
            return new ArrayList<>();
        }



    }


}
