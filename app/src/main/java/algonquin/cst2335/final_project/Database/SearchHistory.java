package algonquin.cst2335.final_project.Database;
/**
 * Purpose: This file conatins
 * Author: Arshpreet Kaur
 * Lab Section: 022
 * Creation Date: 31 March 2024
 */
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "searchHistory")
public class SearchHistory {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String searchTerm;

    public SearchHistory(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}

// Getters and setters

