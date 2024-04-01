package algonquin.cst2335.final_project.sunrise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.final_project.R;
import algonquin.cst2335.final_project.databinding.FavoriteLocationListBinding;

/**
 * This class represents the activity displaying a list of favorite locations in the Android app
 * It includes functionality to interact with the list,
 * such as adding, updating, and deleting favorite locations
 * Uses Room database for data persistence and RecyclerView for displaying the list
 *
 * @author Tony Chen
 * @version 1.0
 */
public class FavoriteLocationList extends AppCompatActivity {

    /**
     * Binding for the favorite location list layout using view binding
     */
    private FavoriteLocationListBinding favoriteLocationListBinding;
    /**
     * Data Access Object for interacting with the Room database
     */
    private FavoriteLocationDAO DAO;
    /**
     * Adapter for the RecyclerView displaying favorite locations
     */
    private FavoriteLocationAdapter adapter;
    /**
     * List to store favorite locations retrieved from the database
     */
    private final ArrayList<FavoriteLocation> favorite_locations = new ArrayList<>();

    /**
     * Called when the activity is first created
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.
     * Note: Otherwise it is null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        favoriteLocationListBinding = FavoriteLocationListBinding.inflate(getLayoutInflater());
        setContentView(favoriteLocationListBinding.getRoot());

        initializeDatabase();

        setupRecyclerView();

        setupGoBackButton();

        retrieveDataFromIntent();

        insertFavoriteLocationIntoDatabase();
    }

    /**
     * Initialize the Room database and set up the RecyclerView
     */
    private void initializeDatabase() {
        LocationDatabase DB = Room.databaseBuilder(getApplicationContext(), LocationDatabase.class, "locationData")
                .build();
        DAO = DB.favorite_Location_DAO();
    }

    /**
     * Set up the RecyclerView to display favorite locations
     */
    private void setupRecyclerView() {
        favoriteLocationListBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoriteLocationAdapter();
        favoriteLocationListBinding.recyclerView.setAdapter(adapter);
    }

    /**
     * Set up the "Go Back" button to navigate back to the main activity
     */
    private void setupGoBackButton() {
        Button goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(click -> startActivity(new Intent(this, SunriseActivity.class)));
    }

    /**
     * Retrieve data from the intent and insert a favorite location into the Room database
     */
    private void retrieveDataFromIntent() {
        String timezone = getIntent().getStringExtra("timezone");
        String sunrise = getIntent().getStringExtra("sunrise");
        String sunset = getIntent().getStringExtra("sunset");
        String longitude = getIntent().getStringExtra("longitudeInput");
        String latitude = getIntent().getStringExtra("latitudeInput");

        FavoriteLocation favorite_location_order = new FavoriteLocation(latitude, longitude, timezone, sunrise, sunset);

        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            if (favorite_location_order.getTimezone() != null) {
                DAO.insertFavoriteLocation(favorite_location_order);
            }

            List<FavoriteLocation> allLocations = DAO.getAllFavoriteLocations();
            runOnUiThread(() -> {
                favoriteAdapterUpdateUI(allLocations);
            });
        });
    }

    /**
     * Update the UI of the favorite location list adapter with the provided list of locations
     *
     * @param allLocations List of all favorite locations retrieved from the database
     */
    private void favoriteAdapterUpdateUI(List<FavoriteLocation> allLocations) {
        favorite_locations.clear();
        favorite_locations.addAll(allLocations);
        adapter.notifyDataSetChanged();
    }

    /**
     * Insert a favorite location into the Room database
     */
    private void insertFavoriteLocationIntoDatabase() {
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            List<FavoriteLocation> allLocations = DAO.getAllFavoriteLocations();
            runOnUiThread(() -> favoriteAdapterUpdateUI(allLocations));
        });
    }

    /**
     * RecyclerView adapter for displaying favorite locations
     */
    class FavoriteLocationAdapter extends RecyclerView.Adapter<MyRowHolder> {

        @Override
        public MyRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.favorite_location_elements, parent, false);
            return new MyRowHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyRowHolder holder, int position) {
            SimpleDateFormat date_format = new SimpleDateFormat("dd-MMM-yyyy hh-mm-ss");
            String current_date_and_time = date_format.format(new Date());

            FavoriteLocation favorite_location_position = favorite_locations.get(position);

            holder.sunrise_favorite_output.setText(getString(R.string.sunrise_sentence) + " " + favorite_location_position.getSunrise());
            holder.sunset_favorite_output.setText(getString(R.string.sunset_sentence) + " " + favorite_location_position.getSunset());
            holder.timezone_favorite_output.setText(getString(R.string.time_zone_sentence) + " " + favorite_location_position.getTimezone());
            holder.timeStamp_favorite_output.setText(getString(R.string.date_time_sentence) + " " + current_date_and_time);

            holder.itemView.setOnClickListener(click -> delete_or_update(position));
        }

        @Override
        public int getItemCount() {
            return favorite_locations.size();
        }
    }

    /**
     * ViewHolder for the RecyclerView item representing a favorite location
     */
    class MyRowHolder extends RecyclerView.ViewHolder {

        /**
         * TextView for displaying the sunrise time of the favorite location in the list item
         */
        public TextView sunrise_favorite_output;
        /**
         * TextView for displaying the sunset time of the favorite location in the list item
         */
        public TextView sunset_favorite_output;
        /**
         * TextView for displaying the timezone of the favorite location in the list item
         */
        public TextView timezone_favorite_output;
        /**
         * TextView for displaying the timestamp (date and time) of the favorite location in the list item
         */
        public TextView timeStamp_favorite_output;

        /**
         * Constructs a new MyRowHolder with references to the TextViews in the item layout
         *
         * @param itemView is the root view of the RecyclerView item
         */
        public MyRowHolder(View itemView) {
            super(itemView);
            sunrise_favorite_output = itemView.findViewById(R.id.sunrise_for_list);
            sunset_favorite_output = itemView.findViewById(R.id.sunset_for_list);
            timezone_favorite_output= itemView.findViewById(R.id.timezone_for_list);
            timeStamp_favorite_output = itemView.findViewById(R.id.current_date_time_for_list);
        }
    }

    /**
     * Display an alert dialog for delete or update options when a list item is clicked
     *
     * @param position Position of the clicked item in the list
     */
    private void delete_or_update(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_title);
        builder.setMessage(R.string.alert_sentence);

        builder.setPositiveButton(R.string.delete_option, (dialog, which) -> {
            delete(position);
        });

        builder.setNegativeButton(R.string.update_option, (dialog, which) -> {
            update(position);
            Toast.makeText(this, R.string.update_sentence, Toast.LENGTH_SHORT).show();
            delete(position);
        });

        builder.show();
    }

    /**
     * Delete a favorite location from the list and Room database
     *
     * @param position Position of the item to be deleted
     */
    private void delete(int position) {
        FavoriteLocation deletedLocation = favorite_locations.get(position);
        favorite_locations.remove(position);
        adapter.notifyItemRemoved(position);

        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> DAO.deleteFavoriteLocation(deletedLocation));

        //Show Notification and action button
        View rootView = findViewById(android.R.id.content); // This gets the root view of the activity
        Snackbar.make(rootView, getResources().getString(R.string.delete_sentence), Snackbar.LENGTH_LONG)
                .setAction(getResources().getString(R.string.undo), btn -> {
                    thread.execute(() -> {
                        DAO.insertFavoriteLocation(deletedLocation);
                    });

                    favorite_locations.add(position, deletedLocation);
                    adapter.notifyItemInserted(position);
                })
                .show();
    }

    /**
     * Update a favorite location and navigate to the location activity for further update
     *
     * @param position Position of the item to be updated
     */
    private void update(int position) {
        FavoriteLocation click_location = favorite_locations.get(position);
        String latitude = click_location.getLatitude();
        String longitude = click_location.getLongitude();

        Intent update_location = new Intent(this, LocationActivity.class);
        update_location.putExtra("latitude", latitude);
        update_location.putExtra("longitude", longitude);
        startActivity(update_location);
    }
}