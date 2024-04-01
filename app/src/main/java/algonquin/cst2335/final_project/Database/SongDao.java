package algonquin.cst2335.final_project.Database;
/**
 * Purpose: This file conatins
 * Author: Arshpreet Kaur
 * Lab Section: 022
 * Creation Date: 31 March 2024
 */
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.final_project.Database.Songd;
import algonquin.cst2335.final_project.Database.Songd;

@Dao
public interface SongDao {
    @Insert
    void insert(Songd song);

    @Query("SELECT * FROM songs")
    List<Songd> getAllFavoriteSongs();

    @Query("DELETE FROM songs")
    void deleteAll();


}

