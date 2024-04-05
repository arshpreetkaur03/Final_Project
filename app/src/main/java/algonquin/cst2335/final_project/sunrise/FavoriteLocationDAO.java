package algonquin.cst2335.final_project.sunrise;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object (DAO) interface for performing database operations
 * on FavoriteLocation entities
 *
 * @author Tony Chen
 * @version 1.0
 */
@Dao
public interface FavoriteLocationDAO {

    /**
     * Inserts a new favorite location into the database
     *
     * @param is_a_favorite_location an object to be inserted
     * @return The row ID of the newly inserted favorite location
     */
    @Insert
    public long insertFavoriteLocation(FavoriteLocation is_a_favorite_location);

    /**
     * Retrieves all favorite locations from the database
     *
     * @return A list of all FavoriteLocation objects in the database
     */
    @Query("SELECT * FROM FavoriteLocation;")
    public List<FavoriteLocation> getAllFavoriteLocations();

    /**
     * Deletes a specific favorite location from the database
     *
     * @param is_a_favorite_location an object to be deleted
     */
    @Delete
    void deleteFavoriteLocation(FavoriteLocation is_a_favorite_location);
}