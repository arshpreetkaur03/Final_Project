package algonquin.cst2335.final_project.Database;
/**
 * Purpose: This file conatins
 * Author: Arshpreet Kaur
 * Lab Section: 022
 * Creation Date: 31 March 2024
 */
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SearchHistoryDao {
    @Insert
    void insert(SearchHistory searchHistory);

    @Query("SELECT * FROM searchHistory ORDER BY id DESC")
    List<SearchHistory> getAllSearchTerms();

    @Query("DELETE FROM searchHistory")
    void deleteAll();
}
