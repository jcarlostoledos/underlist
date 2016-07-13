package underdog.underlist;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eric on 12/07/16.
 *
 */
public class AppController extends Application {

    private Retrofit retrofit;
    private static AppController mInstance;
    private SharedPreferences preferences;
    private String userId;


    public AppController() {
        mInstance = this;
    }

    public static Context getContext() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        final String apiBaseUrl = getContext().getResources().getString(R.string.base_url);

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(apiBaseUrl)
                .build();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


    }

    public String getUserId(){
        return preferences.getString("userId", "0");
    }

    public void setUserId(String userId){
        preferences.edit().putString("userId", userId).apply();
    }


    public String getListId(){
        return preferences.getString("listId", "0");
    }

    public void setListId(String userId){
        preferences.edit().putString("listId", userId).apply();
    }



    public Retrofit getRetrofit() {
        return retrofit;
    }

}
