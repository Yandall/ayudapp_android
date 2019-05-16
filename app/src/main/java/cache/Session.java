package cache;

import android.content.Context;
import android.content.SharedPreferences;


public class Session {
    private SharedPreferences sharedPrefs;
    private final String CACHE_ID_KEY = "ID";

    public Session(Context context){
        sharedPrefs = context.getSharedPreferences("Session", context.MODE_PRIVATE);
    }

    public String getSessionIdCache(){
        return this.sharedPrefs.getString(this.CACHE_ID_KEY, null);
    }

    public void saveSessionIdCache(String id){
        SharedPreferences.Editor editor = this.sharedPrefs.edit();
        editor.putString(this.CACHE_ID_KEY, id);
        editor.commit();
        editor.apply();
    }
}
