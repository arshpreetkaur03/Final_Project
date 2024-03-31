package algonquin.cst2335.final_project;


/**
 * Represents a song with details such as title, artist name, album name, cover URL, and duration.
 * Provides functionality to get these details and to format the song duration from seconds to a readable format.
 */
public class Song {
    private String title;
    private String artistName;
    private String albumName;
    private String coverUrl;
    private int duration; // Duration in seconds

    /**
     * Constructs a new Song with specified details.
     *
     * @param title      the title of the song
     * @param artistName the name of the artist
     * @param albumName  the name of the album
     * @param coverUrl   the URL for the album cover image
     * @param duration   the duration of the song in seconds
     */
    public Song(String title, String artistName, String albumName, String coverUrl, int duration) {
        this.title = title;
        this.artistName = artistName;
        this.albumName = albumName;
        this.coverUrl = coverUrl;
        this.duration = duration;
    }

    // Getters

    /**
     * Returns the title of the song.
     *
     * @return the title of the song
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the name of the artist.
     *
     * @return the name of the artist
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * Returns the name of the album.
     *
     * @return the name of the album
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * Returns the URL for the album cover image.
     *
     * @return the URL for the album cover image
     */
    public String getCoverUrl() {
        return coverUrl;
    }

    /**
     * Returns the duration of the song in seconds.
     *
     * @return the duration of the song in seconds
     */
    public int getDuration() {
        return duration;
    }

    // Additional functionality

    /**
     * Returns the duration of the song formatted as a string in the format "mm:ss".
     *
     * @return the formatted duration of the song
     */
    public String getFormattedDuration() {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
