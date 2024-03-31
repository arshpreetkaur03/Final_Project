package algonquin.cst2335.final_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SongDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "song_database";
    private static final int DATABASE_VERSION = 1;

    // Table name and column names
    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ARTIST = "artist";
    public static final String COLUMN_ALBUM = "album";
    public static final String COLUMN_COVER_URL = "cover_url";
    public static final String COLUMN_DURATION = "duration";

    // SQL statement to create the songs table
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_SONGS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_ARTIST + " TEXT, " +
                    COLUMN_ALBUM + " TEXT, " +
                    COLUMN_COVER_URL + " TEXT, " +
                    COLUMN_DURATION + " INTEGER)";

    public SongDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the songs table
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        // For simplicity, we'll just drop and recreate the table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        onCreate(db);
    }
}

