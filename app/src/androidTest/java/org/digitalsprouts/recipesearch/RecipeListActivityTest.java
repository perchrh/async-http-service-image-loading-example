package org.digitalsprouts.recipesearch;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
import static android.support.test.InstrumentationRegistry.getInstrumentation;


@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {

    private static final int[] ROTATIONS = new int[]{
            SCREEN_ORIENTATION_LANDSCAPE,
            SCREEN_ORIENTATION_PORTRAIT,
            SCREEN_ORIENTATION_REVERSE_LANDSCAPE,
            SCREEN_ORIENTATION_REVERSE_PORTRAIT};
    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityRule =
            new ActivityTestRule<>(RecipeListActivity.class, true, false);

    @Test
    public void ensureActivityCanStart() {
        startActivity();
    }

    // Review: Here we could also test the UI, do a search and
    // see that we can select an item in the search results to launch a new activity
    // This is best to do without an actual network service call, for test stability,
    // for example by using the MockWebServer from OkHttp.

    @Test
    public void ensureRotationDoesNotResultInCrash() {
        startActivity();
        RecipeListActivity testSubject = mActivityRule.getActivity();
        getInstrumentation().waitForIdleSync();
        for (int r : ROTATIONS) {
            testSubject.setRequestedOrientation(r);
            getInstrumentation().waitForIdleSync(); // wait until complete
        }
    }

    private void startActivity() {
        mActivityRule.launchActivity(null);
    }

}