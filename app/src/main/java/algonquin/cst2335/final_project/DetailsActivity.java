package algonquin.cst2335.final_project;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    private ImageView albumCoverImageView;
    private TextView titleTextView, artistTextView, albumTextView, durationTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

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
