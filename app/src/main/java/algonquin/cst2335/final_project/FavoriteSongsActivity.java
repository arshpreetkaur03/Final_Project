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

import algonquin.cst2335.final_project.Database.AppDatabase;
import algonquin.cst2335.final_project.Database.Songd;

public class FavoriteSongsActivity extends AppCompatActivity {
    private ListView listView;
    private List<Song> favoriteSongsList;
    private ArrayAdapter<Song> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_songs);

        listView = findViewById(R.id.favorite_songs_list);

        // Initialize the adapter with an empty list; this will be updated once we fetch songs
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listView.setAdapter(adapter);

        fetchFavoriteSongs();
    }

    private void fetchFavoriteSongs() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Songd> savedSongs = AppDatabase.getDatabase(getApplicationContext()).songDao().getAllFavoriteSongs();
                // Assuming you have a method to convert Songd objects to your Song class
                List<Song> songs = convertSongdToSong(savedSongs);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        adapter.addAll(songs);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
    private List<Song> convertSongdToSong(List<Songd> savedSongs) {
        List<Song> songs = new ArrayList<>();
        for (Songd savedSong : savedSongs) {
            Song song = new Song(savedSong.getTitle(), savedSong.getArtistName(), savedSong.getAlbumName(), savedSong.getCoverUrl(),savedSong.getDuration());
            songs.add(song);
        }
        return songs;
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

