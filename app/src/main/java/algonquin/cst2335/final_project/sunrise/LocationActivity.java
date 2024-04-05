package algonquin.cst2335.final_project.sunrise;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import algonquin.cst2335.final_project.R;

/**
 * An Android activity that displays information about
 * the sunrise, sunset, and time zone of a specific location
 * Users can save this location to a favorites list
 *
 * @author Tony Chen
 * @version 1.0
 */
public class LocationActivity extends AppCompatActivity {

    /**
     * TextView for displaying the time zone information
     */
    private TextView timeZoneInfo;
    /**
     * TextView for displaying the sunrise information
     */
    private TextView sunriseInfo;
    /**
     * TextView for displaying the sunset information
     */
    private TextView sunsetInfo;
    /**
     * Latitude of the location
     */
    private String latitude;
    /**
     * Longitude of the location
     */
    private String longitude;
    /**
     * Button for saving the location to favorites
     */
    private Button saveButton;
    /**
     * Button for returning to the main activity
     */
    private Button backToMain;

    /**
     * Initializes the activity, sets up the UI elements, and retrieves location information
     * from the intent
     * Initiates an asynchronous task to fetch sunrise, sunset, and time zone information
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}
     * Note: Otherwise it is null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_location_output);

        timeZoneInfo = findViewById(R.id.time_zone_output);
        sunriseInfo = findViewById(R.id.sunrise_output);
        sunsetInfo = findViewById(R.id.sunset_output);
        saveButton = findViewById(R.id.save_location_button);
        backToMain = findViewById(R.id.back_button);

        Intent intent = getIntent();
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");

        // Initialize the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            new SunriseAndSunsetTask(latitude, longitude, requestQueue); // Start the request directly
        }

        saveButton.setOnClickListener(click -> {
            String timeZoneOutputFavorite = timeZoneInfo.getText().toString();
            String sunriseOutputFavorite = sunriseInfo.getText().toString();
            String sunsetOutputFavorite = sunsetInfo.getText().toString();

            Intent sentToFavorite = new Intent(this, FavoriteLocationList.class);

            sentToFavorite.putExtra("latitudeInput", latitude);
            sentToFavorite.putExtra("longitudeInput", longitude);
            sentToFavorite.putExtra("timezone", timeZoneOutputFavorite);
            sentToFavorite.putExtra("sunrise", sunriseOutputFavorite);
            sentToFavorite.putExtra("sunset", sunsetOutputFavorite);

            startActivity(sentToFavorite);

            Toast.makeText(this, R.string.save_location_sentence, Toast.LENGTH_LONG).show();
        });

        backToMain.setOnClickListener(click -> {
            Intent backToMain = new Intent(this, SunriseActivity.class);
            startActivity(backToMain);
        });
    }

    /**
     * Inner class representing an asynchronous task to fetch sunrise, sunset, and time zone information
     */
    public class SunriseAndSunsetTask {

        /**
         * The latitude of the location for which to fetch sunrise and sunset information
         */
        private final String latitude;
        /**
         * The longitude of the location for which to fetch sunrise and sunset information
         */
        private final String longitude;
        /**
         * The RequestQueue to be used for making network requests
         * It is responsible for handling and scheduling the HTTP requests
         */
        private final RequestQueue requestQueue;

        /**
         * Constructor for the task
         *
         * @param latitude is the latitude of the location
         * @param longitude is the longitude of the location
         * @param requestQueue is to be used for making network requests
         */
        public SunriseAndSunsetTask(String latitude, String longitude, RequestQueue requestQueue) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.requestQueue = requestQueue;

            execute(); // Initiate the request in the constructor
        }

        /**
         * Executes the task by making an HTTP GET request to an external API
         */
        private void execute() {
            String urlBuilder = buildUrl(Double.parseDouble(latitude), Double.parseDouble(longitude));
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    urlBuilder,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                handleResult(response.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }
            );

            // Add the request to the RequestQueue
            requestQueue.add(jsonObjectRequest);
        }

        /**
         * Builds the URL for the API request based on the provided latitude and longitude
         *
         * @param latitude is the latitude of the location
         * @param longitude is the longitude of the location
         * @return The constructed URL
         */
        @NonNull
        private String buildUrl(double latitude, double longitude) {
            return String.format("https://api.sunrisesunset.io/json?lat=%f&lng=%f", latitude, longitude);
        }

        /**
         * Handles the result obtained from an API response containing location information
         *
         * @param result is the JSON string representing the API response containing location information
         * @throws JSONException is to display if there is an issue parsing the JSON result
         */
        private void handleResult(String result) throws JSONException {
            if (result != null) {
                JSONObject jsonResponse = new JSONObject(result);
                JSONObject results = jsonResponse.getJSONObject("results");

                // Update UI components with the extracted information
                runOnUiThread(() -> {
                    timeZoneInfo.setText(results.optString("timezone"));
                    sunriseInfo.setText(results.optString("sunrise"));
                    sunsetInfo.setText(results.optString("sunset"));
                });
            } else {
                // Display an error message if the result is null
                runOnUiThread(() ->
                        Toast.makeText(LocationActivity.this, R.string.error_message, Toast.LENGTH_LONG).show()
                );
            }
        }
    }
}