package algonquin.cst2335.final_project;
/**
 * Purpose: This file conatins
 * Author: Arshpreet Kaur
 * Lab Section: 022
 * Creation Date: 31 March 2024
 */
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.squareup.picasso.Picasso;

import algonquin.cst2335.final_project.Database.AppDatabase;
import algonquin.cst2335.final_project.Database.SongDao;
import algonquin.cst2335.final_project.Database.Songd;
import algonquin.cst2335.final_project.Song;



public class DetailsActivity extends AppCompatActivity {
    private ImageView albumCoverImageView;
    private TextView titleTextView, artistTextView, albumTextView, durationTextView;
    private Songd songdb; // Assuming you want to use it for something

    // Make db a class-wide field to ensure it's accessible throughout the class
    private AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialize Room database
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "songs_database")
                .fallbackToDestructiveMigration() // Handle schema changes without proper migration; use with caution
                .build();

        // Initialize views
        albumCoverImageView = findViewById(R.id.album_cover_image);
        titleTextView = findViewById(R.id.song_title);
        artistTextView = findViewById(R.id.artist_name);
        albumTextView = findViewById(R.id.album_name);
        durationTextView = findViewById(R.id.song_duration);

        // Final variable to hold the song object for use in inner class
        final Song song = (Song) getIntent().getSerializableExtra("selectedSong");
        if (song != null) {
            displaySongDetails(song);
        }

        findViewById(R.id.save_button).setOnClickListener(v -> {
            if (song != null) {
                saveSongToDatabase(song);
            }
        });
    }

    private void displaySongDetails(Song song) {
        titleTextView.setText(song.getTitle());
        artistTextView.setText(song.getArtistName());
        albumTextView.setText(song.getAlbumName());
        durationTextView.setText(song.getFormattedDuration());
        Picasso.get().load(song.getCoverUrl()).into(albumCoverImageView);
    }

    private void saveSongToDatabase(Song song) {
        Songd songd = new Songd(song.getTitle(), song.getArtistName(), song.getAlbumName(), song.getDuration(), song.getCoverUrl());
        new Thread(() -> {
            db.songDao().insert(songd);
            runOnUiThread(() -> Toast.makeText(DetailsActivity.this, "Song saved to favorites", Toast.LENGTH_SHORT).show());
        }).start();
    }
}
