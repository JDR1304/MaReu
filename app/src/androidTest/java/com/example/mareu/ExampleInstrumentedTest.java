package com.example.mareu;

import android.content.Context;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.controller.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    // This is fixed
    private static int ITEMS_COUNT = 10;

    private MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least one item
     */
    @Test
    public void myMeetingsList_shouldNotBeEmpty() {

        onView(allOf(withId(R.id.fragment_meeting_recyclerview), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }
    /**
     * Test if the fragment AddOrMeetingDetailsFragment is launched
     */
    @Test
    public void clickOnItemForDetails() {

        onView(allOf(ViewMatchers.withId(R.id.fragment_meeting_recyclerview), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(allOf(ViewMatchers.withId(R.id.RoomName), isDisplayed()));
        //onView(withId(R.id.fragment_add_or_meeting_details)).check(matches(isDisplayed()));
    }
    /**
     * Test if the Floating button works
     */
    @Test
    public void clickOnFloatingButtonForAdd() {

        onView(allOf(ViewMatchers.withId(R.id.floatingActionButton), isDisplayed()))
                .perform(click());
        onView(allOf(ViewMatchers.withId(R.id.RoomName), isDisplayed()));
        //onView(withId(R.id.fragment_add_or_meeting_details)).check(matches(isDisplayed()));
    }
    /*
    @Test
    public void DeleteAnItem(){
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.fragment_meeting_recyclerview), isDisplayed())).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.fragment_meeting_recyclerview), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.fragment_meeting_recyclerview), isDisplayed())).check(withItemCount(ITEMS_COUNT - 1));

    }*/
}