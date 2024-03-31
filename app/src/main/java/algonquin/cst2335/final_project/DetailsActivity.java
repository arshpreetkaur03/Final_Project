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
        if (getIntent().getExtras() != null) {
            String title = getIntent().getStringExtra("title");
            String artist = getIntent().getStringExtra("artist");
            String album = getIntent().getStringExtra("album");
            String coverUrl = getIntent().getStringExtra("coverUrl");
            String duration = getIntent().getStringExtra("duration");

            // Set song details to views
            setTitle(title);
            titleTextView.setText(title);
            artistTextView.setText(artist);
            albumTextView.setText(album);
            durationTextView.setText(duration);

            // Load album cover image using Picasso library
            Picasso.get().load(coverUrl).into(albumCoverImageView);
        }
    }
}
