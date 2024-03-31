package algonquin.cst2335.final_project;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ApiResponseHandler {
    private static final String TAG = ApiResponseHandler.class.getSimpleName();

    public static List<Song> parseSongsFromJson(JSONObject jsonResponse) {
        List<Song> songsList = new ArrayList<>();

        try {
            JSONArray data = jsonResponse.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject songJson = data.getJSONObject(i);
                String title = songJson.getString("title");
                String artistName = songJson.getJSONObject("artist").getString("name");
                String albumName = songJson.getJSONObject("album").getString("title");
                String coverUrl = songJson.getJSONObject("album").getString("cover");
                int duration = songJson.getInt("duration");

                Song song = new Song(title, artistName, albumName, coverUrl, duration);
                songsList.add(song);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
        }

        return songsList;
    }
}
