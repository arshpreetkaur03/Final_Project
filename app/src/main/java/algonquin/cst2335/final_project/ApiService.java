package algonquin.cst2335.final_project;

/**
 * Purpose: This file conatins
 * Author: Arshpreet Kaur
 * Lab Section: 022
 * Creation Date: 31 March 2024
 */

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class ApiService {
    private static final String TAG = ApiService.class.getSimpleName();
    private static final String BASE_URL = "https://api.deezer.com/";

    private RequestQueue requestQueue;
    private Context context;

    public ApiService(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void searchArtist(String artistQuery, final ApiResponseListener listener) {
        String url = BASE_URL + "search?q=" + artistQuery; // Adjusted for song/artist search

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccess(response); // Pass the JSON response directly to the listener
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error in API request: " + error.getMessage());
                        listener.onError("Error in API request");
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
    public void fetchSongsFromTracklist(String tracklistUrl, final ApiResponseListener listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                tracklistUrl,
                null,
                response -> listener.onSuccess(response),
                error -> {
                    Log.e(TAG, "Error in API request: " + error.getMessage());
                    listener.onError("Error in API request");
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


    public interface ApiResponseListener {
        void onSuccess(JSONObject data);
        void onError(String errorMessage);
    }
}
