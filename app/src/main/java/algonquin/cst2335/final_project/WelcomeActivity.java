package algonquin.cst2335.final_project;

import static algonquin.cst2335.final_project.R.id.Sunrise_Sunset_Page;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.final_project.sunrise.SunriseActivity;

/**
 * This class represents the main activity of the Android application
 * It handles user interactions to switch between different app functions
 *
 * @author Tony Chen
 * @version 1.0
 */

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Find buttons by ID
        Button buttonPage1 = findViewById(Sunrise_Sunset_Page);
        Button buttonPage2 = findViewById(R.id.Recipe_Search_Page);
        Button buttonPage3 = findViewById(R.id.Dictionary_API_Page);
        Button buttonPage4 = findViewById(R.id.Deezer_Song_Search_API_Page);

        // Set click listeners for each button
        buttonPage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, SunriseActivity.class));
            }
        });

        buttonPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, WelcomeActivity.class));
            }
        });

        buttonPage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, WelcomeActivity.class));
            }
        });


        buttonPage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, DeezerSongActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_back) {
            Intent backToMain = new Intent(this, WelcomeActivity.class);
            startActivity(backToMain);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}