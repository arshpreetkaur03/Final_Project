package algonquin.cst2335.final_project;

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
        Songd songdb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //room database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "songs").build();
        SongDao songDao = db.songDao();
        // Initialize views
        albumCoverImageView = findViewById(R.id.album_cover_image);
        titleTextView = findViewById(R.id.song_title);
        artistTextView = findViewById(R.id.artist_name);
        albumTextView = findViewById(R.id.album_name);
        durationTextView = findViewById(R.id.song_duration);

        // Get the Song object from intent extras
        Song song = (Song) getIntent().getSerializableExtra("selectedSong");
        if (song != null) {
            // Use the Song object to set details in views
            titleTextView.setText(song.getTitle());
            artistTextView.setText(song.getArtistName());
            albumTextView.setText(song.getAlbumName());
            durationTextView.setText(song.getFormattedDuration());

            // Load album cover image using Picasso or Glide
            Picasso.get().load(song.getCoverUrl()).into(albumCoverImageView);
        }
        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (song != null) {
                    Songd songd = new Songd(song.getTitle(), song.getArtistName(), song.getAlbumName(), song.getDuration(), song.getCoverUrl());
                    new Thread(() -> {
                        db.songDao().insert(songd);
                    }).start();
                Toast.makeText(DetailsActivity.this, "Song saved to favorites", Toast.LENGTH_SHORT).show();
            }
        }
});
    }
    /**
     * Formats the duration from seconds into a mm:ss format.
     *
     * @param durationInSeconds The duration in seconds.
     * @return The formatted duration as a String.
     */
    private String formatDuration(int durationInSeconds) {
        int minutes = durationInSeconds / 60;
        int seconds = durationInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
