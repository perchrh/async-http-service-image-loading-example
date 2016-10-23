package org.digitalsprouts.recipesearch;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.digitalsprouts.recipesearchclient.model.Recipe;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
import static android.support.test.InstrumentationRegistry.getInstrumentation;


@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    private static final int[] ROTATIONS = new int[]{
            SCREEN_ORIENTATION_LANDSCAPE,
            SCREEN_ORIENTATION_PORTRAIT,
            SCREEN_ORIENTATION_REVERSE_LANDSCAPE,
            SCREEN_ORIENTATION_REVERSE_PORTRAIT};
    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityRule =
            new ActivityTestRule<>(RecipeDetailActivity.class, true, false);

    @Test
    public void ensureActivityCanStart() throws Exception {
        startActivity();
    }

    private void startActivity() {
        Intent intent = new Intent();
        Recipe data = MockData.getMockRecipe();
        intent.putExtra(StartRecipeDetailActivityIntent.EXTRA_KEY_RECIPE_ITEM, data);

        mActivityRule.launchActivity(intent);
    }

    @Test
    public void ensureRotationDoesNotResultInCrash() throws Exception {
        startActivity();
        RecipeDetailActivity testSubject = mActivityRule.getActivity();

        getInstrumentation().waitForIdleSync();
        for (int r : ROTATIONS) {
            testSubject.setRequestedOrientation(r);
            getInstrumentation().waitForIdleSync(); // wait until complete
        }
    }

}