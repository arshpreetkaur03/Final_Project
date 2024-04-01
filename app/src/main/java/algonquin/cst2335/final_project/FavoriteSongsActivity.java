package algonquin.cst2335.final_project;

import android.content.Intent;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.final_project.Database.AppDatabase;
import algonquin.cst2335.final_project.Database.Songd;

public class FavoriteSongsActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> favoriteSongsTitles = new ArrayList<>();
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_songs);

        listView = findViewById(R.id.favorite_songs_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteSongsTitles);
        listView.setAdapter(adapter);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "songs_database")
                .fallbackToDestructiveMigration()
                .build();

        fetchFavoriteSongs();
    }

    private void fetchFavoriteSongs() {
        new Thread(() -> {
            List<Songd> songs = db.songDao().getAllFavoriteSongs();
            favoriteSongsTitles.clear();
            for (Songd song : songs) {
                favoriteSongsTitles.add(song.getTitle());
                favoriteSongsTitles.add(song.getArtistName());// Example: Fetching only the title, adapt as needed.
            }
            // Update UI on UI Thread
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        }).start();
    }
}


