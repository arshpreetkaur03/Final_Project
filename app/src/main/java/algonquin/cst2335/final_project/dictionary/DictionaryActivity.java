package algonquin.cst2335.final_project.dictionary;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import algonquin.cst2335.final_project.databinding.DictionaryMainBinding;


public class DictionaryActivity extends AppCompatActivity {

    private DictionaryMainBinding binding = null;
    private DefinitionDao dao;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DictionaryMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Room database
        final Database db = Room.databaseBuilder(getApplicationContext(),
                        Database.class, "DefinitionDB")
                .fallbackToDestructiveMigration() // Handle version upgrade by clearing the database
                .build();
        dao = db.defDao();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Startup fragment
        setFragment(new SearchFragment(), false);
    }

    public void setFragment(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(binding.fragmentContainerView.getId(), fragment); // Access root view and use correct container ID
        if (addToBackstack) t.addToBackStack(null);
        t.commit();
    }

    public DefinitionDao getDefinitionDao() {
        return dao;
    }
}
