package algonquin.cst2335.final_project.sunrise;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Room Database class for managing favorite locations
 *
 * @author Tony Chen
 * @version 1.0
 */
@Database(entities = {FavoriteLocation.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {

    /**
     * Abstract method to retrieve the DAO (Data Access Object) for FavoriteLocation
     *
     * @return The FavoriteLocationDAO for database operations
     */
    public abstract FavoriteLocationDAO favorite_Location_DAO();
}