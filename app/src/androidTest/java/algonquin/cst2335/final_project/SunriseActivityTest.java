package algonquin.cst2335.final_project;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import algonquin.cst2335.final_project.sunrise.LocationActivity;
import algonquin.cst2335.final_project.sunrise.SunriseActivity;

@RunWith(AndroidJUnit4.class)
public class SunriseActivityTest {

    private String lat = "45.424721";
    private String lon = "-75.695000";

    private SharedPreferences sharedPreferences;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
    }

    @After
    public void tearDown() {
        sharedPreferences.edit().clear().apply();
    }



    @Test
    public void testSunriseLookUpButtons() {


        ActivityScenario.launch(SunriseActivity.class);

        // Check if all the inputs and buttons are displayed after searching
        Espresso.onView(withId(R.id.latitude_input)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.longitude_input)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.lookup_button)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.view_favorite_locations_button)).check(matches(isDisplayed()));


    }


    @Test
    public void testSunriseLookupButton() {
        // Launch the activity
        ActivityScenario.launch(SunriseActivity.class);

        // Clear Input Fields
        Espresso.onView(withId(R.id.menu_clear)).perform(ViewActions.click());

        // Type a search query into the latitude
        Espresso.onView(ViewMatchers.withId(R.id.latitude_input))
                .perform(ViewActions.typeText(lat), ViewActions.closeSoftKeyboard());

        // Type a search query into the longitude
        Espresso.onView(ViewMatchers.withId(R.id.longitude_input))
                .perform(ViewActions.typeText(lon), ViewActions.closeSoftKeyboard());

        // Click on the lookup
        Espresso.onView(withId(R.id.lookup_button)).perform(ViewActions.click());

        // Check if the time zone is displayed
        Espresso.onView(withId(R.id.time_zone_output)).check(matches(isDisplayed()));

    }





    @Test
    public void testFavoriteLocations() {
        // Launch the activity
        ActivityScenario.launch(SunriseActivity.class);

        // Clear Input Fields
        Espresso.onView(withId(R.id.menu_clear)).perform(ViewActions.click());

        // Click on the favorite locations button
        Espresso.onView(withId(R.id.view_favorite_locations_button)).perform(ViewActions.click());

        // Check if the recycler view from favorite location list is displayed
        Espresso.onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }



    @Test
    public void testBackButton() {
        // Launch the activity
        ActivityScenario.launch(SunriseActivity.class);

        // Clear Input Fields
        Espresso.onView(withId(R.id.menu_clear)).perform(ViewActions.click());


        // Type a search query into the latitude
        Espresso.onView(ViewMatchers.withId(R.id.latitude_input))
                .perform(ViewActions.typeText(lat), ViewActions.closeSoftKeyboard());

        // Type a search query into the longitude
        Espresso.onView(ViewMatchers.withId(R.id.longitude_input))
                .perform(ViewActions.typeText(lon), ViewActions.closeSoftKeyboard());

        // Click on the lookup button
        Espresso.onView(withId(R.id.lookup_button)).perform(ViewActions.click());

        // Click on the back button
        Espresso.onView(withId(R.id.back_button)).perform(ViewActions.click());

        // Check if latitude input field is displayed after going back
        Espresso.onView(withId(R.id.latitude_input)).check(matches(isDisplayed()));
    }

    @Test
    public void testSunriseActivity(){
        // Launch the welcome activity
        ActivityScenario.launch(WelcomeActivity.class);

        // Select Sunrise Sunset App
        Espresso.onView(withId(R.id.Sunrise_Sunset_Page)).perform(ViewActions.click());

        // Check if latitude input field is displayed
        Espresso.onView(withId(R.id.latitude_input)).check(matches(isDisplayed()));

    }


}

