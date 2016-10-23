package org.digitalsprouts.recipesearchclient.model;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/*
Tests the Parcelable behaviour of the class
 */
@RunWith(AndroidJUnit4.class)
public class RecipeTest {
    @Test
    public void testParcelable() throws Exception {
        Recipe testSubject = MockData.getMockRecipe();

        // Obtain a Parcel object and write the parcelable object to it:
        Parcel parcel = Parcel.obtain();
        testSubject.writeToParcel(parcel, 0);

        // After you're done with writing, you need to reset the parcel for reading:
        parcel.setDataPosition(0);

        // Reconstruct object from parcel and asserts:
        Recipe createdFromParcel = Recipe.CREATOR.createFromParcel(parcel);
        assertEquals(testSubject, createdFromParcel);
    }


}
