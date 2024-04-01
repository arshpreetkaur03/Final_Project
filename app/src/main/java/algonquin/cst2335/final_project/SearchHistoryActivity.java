package algonquin.cst2335.final_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import algonquin.cst2335.final_project.Database.AppDatabase;

public class SearchHistoryActivity extends AppCompatActivity {

    private AppDatabase db;
    private Button deleteHistoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);



        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "searchHistory").allowMainThreadQueries().build();

        deleteHistoryButton = findViewById(R.id.Delete_History);
        deleteHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete all search history
                db.searchHistoryDao().deleteAll();
                // Optionally, refresh your RecyclerView to reflect the changes
            }
        });
    }
}
