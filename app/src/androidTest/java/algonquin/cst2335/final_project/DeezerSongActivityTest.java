package algonquin.cst2335.final_project;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DeezerSongActivityTest {

    @Rule
    public ActivityScenarioRule<DeezerSongActivity> activityRule =
            new ActivityScenarioRule<>(DeezerSongActivity.class);

    @Test
    public void testSuccessfulSearch() {
        // Assume 'searchEditText' is the EditText view's ID where the user types the search query
        // and 'searchButton' is the Button view's ID to initiate the search

        // Type the search term and press the search button
        Espresso.onView(withId(R.id.search_edit_text))
                .perform(ViewActions.typeText("Queen"), ViewActions.closeSoftKeyboard());

        Espresso.onView(withId(R.id.search_button)).perform(click());

        // Check if the RecyclerView (assuming its ID is 'recycler_view_songs') displaying the search results is visible
        Espresso.onView(withId(R.id.recycler_view_songs))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Optionally, check for a specific item in the results
        // Espresso.onView(ViewMatchers.withText("Some song by Queen"))
        //         .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


}
