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

@RunWith(AndroidJUnit4.class)
public class ActivityTest {

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
    public void testSearchButton() {
        // Launch the activity
        ActivityScenario.launch(DeezerSongActivity.class);

        // Type a search query into the EditText
        Espresso.onView(ViewMatchers.withId(R.id.search_edit_text))
                .perform(ViewActions.typeText("Despacito"), ViewActions.closeSoftKeyboard());

        // Click on the search button
        Espresso.onView(withId(R.id.search_button)).perform(ViewActions.click());

        // Check if RecyclerView is displayed after searching
        Espresso.onView(withId(R.id.recycler_view_songs)).check(matches(isDisplayed()));
    }

    @Test
    public void testHelpDialog() {
        // Launch the activity
        ActivityScenario.launch(DeezerSongActivity.class);

        // Click on the help menu item
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        Espresso.onView(withText("Help")).perform(ViewActions.click());

        // Check if help dialog is displayed
        Espresso.onView(withText("Help")).check(matches(isDisplayed()));
    }

    @Test
    public void testFavoriteSongsActivity() {
        // Launch the activity
        ActivityScenario.launch(DeezerSongActivity.class);

        // Click on the favorite songs menu item
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        Espresso.onView(withText("Favorite Songs")).perform(ViewActions.click());

        // Check if FavoriteSongsActivity is launched
        Espresso.onView(withId(R.id.favorite_songs_list)).check(matches(isDisplayed()));
    }

    @Test
    public void testBackButton() {
        // Launch the activity
        ActivityScenario.launch(DeezerSongActivity.class);

        // Perform back button press
        Espresso.pressBackUnconditionally();

        // Check if activity is finished
        Espresso.onView(withId(android.R.id.content)).check(matches(isDisplayed()));
    }
}
