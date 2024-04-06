package algonquin.cst2335.final_project.dictionary;
/**
 * Author: Hansvin Venetheethan
 * Purpose: Definition Interface Dao
 */
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DefinitionDao {

    @Query("SELECT DISTINCT word FROM Definition")
    List<String> getAllWords();

    @Query("SELECT * FROM Definition WHERE word = :word")
    List<Definition> getDefinitionsForWord(String word);

    @Insert
    void insertAll(List<Definition> definitions);

    @Query("DELETE FROM Definition WHERE word =:word")
    void deleteDefinitionsForWord(String word);
}
