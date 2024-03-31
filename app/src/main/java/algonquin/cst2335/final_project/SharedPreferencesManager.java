package algonquin.cst2335.final_project;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String SHARED_PREF_NAME = "MySharedPref";
    private static final String KEY_SEARCH_TERM = "searchTerm";

    private SharedPreferences sharedPreferences;
    private static SharedPreferencesManager instance;

    // Public constructor to accept a Context
    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    // Singleton pattern to get a single instance of SharedPreferencesManager
    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context);
        }
        return instance;
    }

    public void saveSearchTerm(String searchTerm) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SEARCH_TERM, searchTerm);
        editor.apply();
    }

    public String getSearchTerm() {
        return sharedPreferences.getString(KEY_SEARCH_TERM, "");
    }

    public void clearSearchTerm() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_SEARCH_TERM);
        editor.apply();
    }
}
