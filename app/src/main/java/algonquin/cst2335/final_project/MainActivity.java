package algonquin.cst2335.final_project;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SongAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private SongAdapter songAdapter;
    private List<Song> songList;
    private ApiService apiService;
    private SharedPreferencesManager sharedPreferencesManager;
    private EditText searchEditText;
    private String searchQuery;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView, SongAdapter, ApiService, and SharedPreferencesManager
        recyclerView = findViewById(R.id.recycler_view_songs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        songList = new ArrayList<>();
        songAdapter = new SongAdapter(songList, this);
        recyclerView.setAdapter(songAdapter);
        apiService = new ApiService(this);
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this);
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Initialize search EditText
        searchEditText = findViewById(R.id.search_edit_text);

        // Example usage:
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery = searchEditText.getText().toString();
                performSongSearch(searchQuery);
                saveSearchTerm(searchQuery);
            }
        });

        // Example of retrieving search term from SharedPreferences and setting it to the EditText
        String savedSearchTerm = sharedPreferencesManager.getSearchTerm();
        if (!savedSearchTerm.isEmpty()) {
            searchEditText.setText(savedSearchTerm);
        }



    }





    private void saveSearchTerm(String searchTerm) {
        sharedPreferencesManager.saveSearchTerm(searchTerm);
    }

    private void performSongSearch(String query) {
        try {
            apiService.searchArtist(query, new ApiService.ApiResponseListener() {
                @Override
                public void onSuccess(JSONObject data) {
                    try {
                        List<Song> songs = ApiResponseHandler.parseSongsFromJson(data);
                        if (songs != null) {
                            songList.clear();
                            songList.addAll(songs);
                            songAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("MainActivity", "Error parsing JSON response");
                            Toast.makeText(MainActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e("MainActivity", "Error: " + e.getMessage());
                        Toast.makeText(MainActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("MainActivity", "Error: " + errorMessage);
                    Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("MainActivity", "Error: " + e.getMessage());
            Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search Artists");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSongSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle text changes if needed
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            showHelpDialog();
            return true;
        } else if (id == R.id.action_favorite_songs) {
            openFavoriteSongsActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage("Instructions for using the interface...");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openFavoriteSongsActivity() {
        Intent intent = new Intent(this, FavoriteSongsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        // Handle item click to open details activity
        Song selectedSong = songList.get(position);
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("selectedSong", (Serializable) selectedSong);
        startActivity(intent);
        // Show a Snackbar message
        showSnackbar("Song selected: " + selectedSong.getTitle());
    }

    @Override
    public void onBackPressed() {
        // Handle any necessary actions when back button is pressed
        super.onBackPressed();
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

}
