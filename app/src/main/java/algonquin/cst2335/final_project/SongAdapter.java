package algonquin.cst2335.final_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private List<Song> songList;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public SongAdapter(List<Song> songList, OnItemClickListener listener) {
        this.songList = songList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private TextView artistNameTextView;
        private TextView albumNameTextView;
        private TextView durationTextView;
        private ImageView albumCoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.song_title);
            artistNameTextView = itemView.findViewById(R.id.artist_name);
            albumNameTextView = itemView.findViewById(R.id.album_name);
            durationTextView = itemView.findViewById(R.id.song_duration);
            albumCoverImage = itemView.findViewById(R.id.album_cover_image);
            itemView.setOnClickListener(this);



        }

        public void bind(Song song) {
            titleTextView.setText(song.getTitle());
            artistNameTextView.setText(song.getArtistName());
            albumNameTextView.setText(song.getAlbumName());
            durationTextView.setText(song.getFormattedDuration());
            Glide.with(itemView.getContext())
                    .load(song.getCoverUrl())
                    .into(albumCoverImage);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            }
        }
    }


}
