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

        // Get song details from intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("title", "Title not available");
            String artist = extras.getString("artist", "Artist not available");
            String album = extras.getString("album", "Album not available");
            String coverUrl = extras.getString("coverUrl", "");
            int durationInSeconds = extras.getInt("duration", 0);

            // Format duration from seconds to a more readable format (e.g., mm:ss)
            String formattedDuration = formatDuration(durationInSeconds);

            // Set song details to views
            setTitle(title); // Consider if you want to set the activity title to the song title
            titleTextView.setText(title);
            artistTextView.setText(artist);
            albumTextView.setText(album);
            durationTextView.setText(formattedDuration);

            // Load album cover image using Picasso library
            if (!coverUrl.isEmpty()) {
                Picasso.get().load(coverUrl).into(albumCoverImageView);
            }
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
