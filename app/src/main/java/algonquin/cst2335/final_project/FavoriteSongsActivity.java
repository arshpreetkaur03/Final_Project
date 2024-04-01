package algonquin.cst2335.final_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteSongsActivity extends AppCompatActivity {
    private ListView listView;
    private List<Song> favoriteSongsList;
    private ArrayAdapter<Song> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_songs);

        // Initialize views
        listView = findViewById(R.id.favorite_songs_list);

        // Initialize favorite songs list
        favoriteSongsList = new ArrayList<>(); // Assuming this list is populated elsewhere in your app

        // Create ArrayAdapter for the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteSongsList);
        listView.setAdapter(adapter);

        // Set item click listener for ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song selectedSong = favoriteSongsList.get(position);
                openDetailsActivity(selectedSong);
            }
        });
    }

    private void openDetailsActivity(Song song) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("title", song.getTitle());
        intent.putExtra("artist", song.getArtistName());
        intent.putExtra("album", song.getAlbumName());
        intent.putExtra("coverUrl", song.getCoverUrl());
        intent.putExtra("duration", song.getFormattedDuration());
        startActivity(intent);
    }





}

