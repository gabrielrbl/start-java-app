package com.start.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.start.model.Usuario;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(Usuario usuario){
        String id = usuario.getUsuarioId();

        editor.putInt(SESSION_KEY, Integer.parseInt(id)).commit();
    }

    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
    }
}
