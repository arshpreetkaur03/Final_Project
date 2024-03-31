package algonquin.cst2335.final_project;


import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
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
        String url = BASE_URL + "search/artist/?q=" + artistQuery;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the JSON response and pass the result to the listener
                            JSONObject data = response.getJSONObject("data");
                            listener.onSuccess(data);
                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
                            listener.onError("Error parsing JSON response");
                        }
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

    public interface ApiResponseListener {
        void onSuccess(JSONObject data);
        void onError(String errorMessage);
    }
}


