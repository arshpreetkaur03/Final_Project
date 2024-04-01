package algonquin.cst2335.final_project.sunrise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.final_project.R;
import algonquin.cst2335.final_project.databinding.ActivitySunriseBinding;

/**
 * This class is the main activity for the Sunrise Sunset lookup
 * It handles user interactions related to location input
 *
 * @author Tony Chen
 * @version 1.0
 */
public class SunriseActivity extends AppCompatActivity {

    /**
     * Binding for the main activity layout using view binding
     */
    private ActivitySunriseBinding mainActivityBinding;

    /**
     * Strings to store latitude input
     */
    String latitude_input;

    /**
     * Strings to store longitude input
     */
    String longitude_input;

    /**
     * SharedPreferences file name
     */
    private static final String PREFS_NAME = "MyPrefsFile";

    /**
     * Called when the activity is first created
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data
     *                           it most recently supplied in {@link #onSaveInstanceState}.
     * Note: Otherwise it is null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using view binding
        mainActivityBinding = ActivitySunriseBinding.inflate(getLayoutInflater());
        setContentView(mainActivityBinding.getRoot());

        setSupportActionBar(mainActivityBinding.myToolbar);

        // Restore saved values from SharedPreferences
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        mainActivityBinding.latitudeInput.setText(preferences.getString("latitude", ""));
        mainActivityBinding.longitudeInput.setText(preferences.getString("longitude", ""));

        // Set a click listener for the "Lookup" button
        mainActivityBinding.lookupButton.setOnClickListener(view -> {
            latitude_input = mainActivityBinding.latitudeInput.getText().toString();
            longitude_input = mainActivityBinding.longitudeInput.getText().toString();

            /*
            Check if latitude and longitude inputs are not empty
            If not empty, create an intent to start the LocationActivity
            */
            if (!latitude_input.isEmpty() && !longitude_input.isEmpty()) {
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("latitude", latitude_input);
                editor.putString("longitude", longitude_input);
                editor.apply();

                Intent goToLocation = new Intent(this, LocationActivity.class);
                goToLocation.putExtra("latitude", latitude_input);
                goToLocation.putExtra("longitude", longitude_input);
                startActivity(goToLocation);

            } else {
                // Show a toast message if latitude or longitude is empty
                Toast.makeText(this, R.string.latitude_longitude_isEmpty, Toast.LENGTH_LONG).show();
            }
        });

        // Set a click listener for the "View Favorite Locations" button
        mainActivityBinding.viewFavoriteLocationsButton.setOnClickListener(view -> {
            // Create an intent to start the FavoriteLocationList activity
            Intent navigateToFavoriteLocations = new Intent(this, FavoriteLocationList.class);
            startActivity(navigateToFavoriteLocations);
        });
    }

    /**
     * Inflate the options menu in the toolbar
     *
     * @param menu The options menu in which you place your items
     * @return true to display the menu, false otherwise
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        //inflate a menu into the toolbar
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    /**
     * Handle menu item selection in the toolbar
     *
     * @param item The menu item that was selected
     * @return true if the menu item was handled, false otherwise
     */


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_help){
                showHelpDialog();
                return true;
        }

        if (item.getItemId()== R.id.menu_clear) {
            clearInputs();
            return true;
        }

        else{
                return super.onOptionsItemSelected(item);
        }
    }



    /**
     * Show a help dialog with information about the application
     */
    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.about);
        builder.setMessage(R.string.about_message);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Clears input fields or resets relevant state
     */
    private void clearInputs() {
        // Clear the latitude and longitude inputs
        mainActivityBinding.latitudeInput.setText("");
        mainActivityBinding.longitudeInput.setText("");

        // Optionally, clear saved preferences if needed
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.remove("latitude");
        editor.remove("longitude");
        editor.apply();

        Toast.makeText(this, R.string.clear_message, Toast.LENGTH_SHORT).show();
    }
}