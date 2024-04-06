package algonquin.cst2335.final_project.dictionary;
/**
 * Author: Hansvin Venetheethan
 * Class name: Definition
 * Class section: (031)
 */
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Definition {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "word")
    String word;

    @ColumnInfo(name = "definition")
    String definition;

    @ColumnInfo(name = "part_of_speech")
    String partOfSpeech;

    public Definition(String word, String definition, String partOfSpeech) {
        this.word = word;
        this.definition = definition;
        this.partOfSpeech = partOfSpeech;
    }

    // Getters and setters

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }
}
