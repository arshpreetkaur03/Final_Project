package algonquin.cst2335.final_project.dictionary;
/**
 * Author: Hansvin Venetheethan
 * Class name: Room database
 * Class section: (031)
 */

import androidx.room.RoomDatabase;


@androidx.room.Database(entities = {Definition.class}, version = 1)
public abstract class Database extends RoomDatabase {

    public abstract DefinitionDao defDao();
}
