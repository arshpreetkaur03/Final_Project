package algonquin.cst2335.final_project.dictionary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.final_project.R;
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

        final Database db = Room.databaseBuilder(getApplicationContext(),
                        Database.class, "DefinitionDB")
                .fallbackToDestructiveMigration()
                .build();
        dao = db.defDao();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        setFragment(new SearchFragment(), false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about) {
            handleAboutMenuItem();
            return true;
        } else if (id == R.id.help_button) {
            showHelpDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void handleAboutMenuItem() {
        Toast.makeText(this, "Version 1.0, created by Hansvin Venetheethan", Toast.LENGTH_SHORT).show();
    }

    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.help_title))
                .setMessage(getString(R.string.help_info))
                .setNeutralButton("Ok", (dialog, i) -> dialog.dismiss())
                .show();

    }
}
