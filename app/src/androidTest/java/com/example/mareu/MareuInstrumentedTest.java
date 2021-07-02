package com.example.mareu;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.controller.MainActivity;
import com.example.mareu.service.ApiService;
import com.example.mareu.service.DummyApiService;
import com.example.mareu.utils.DeleteViewAction;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.InstrumentationRegistry.getContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MareuInstrumentedTest {

    // This is fixed

    private final ApiService apiService = new DummyApiService();


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
        onView(allOf(ViewMatchers.withId(R.id.roomName), isDisplayed()));
    }

    /**
     * Test if the item could be modify
     */

    @Test
    public void modifyAnItem() {

        onView(allOf(ViewMatchers.withId(R.id.fragment_meeting_recyclerview), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(allOf(withId(R.id.edit_text_reservation_name), isDisplayed()))
                .perform(replaceText("Simon"), closeSoftKeyboard());
        onView(allOf(withId(R.id.validate_button), isDisplayed()))
                .perform(click());
        onView(allOf(withText("Simon"), isDisplayed()));
    }

    /**
     * Test if the Floating button works
     */
    @Test
    public void clickOnFloatingButtonForAdd() {

        onView(allOf(ViewMatchers.withId(R.id.floatingActionButton), isDisplayed()))
                .perform(click());
        onView(allOf(ViewMatchers.withId(R.id.roomName), isDisplayed()));
    }

    /**
     * Test if I can add an item
     */

    @Test
    public void addAnItem() {
        final int ITEMS_COUNT = apiService.getMeetings().size();
        onView(allOf(withId(R.id.fragment_meeting_recyclerview), isDisplayed())).check(withItemCount(ITEMS_COUNT));

        onView(allOf(ViewMatchers.withId(R.id.floatingActionButton), isDisplayed()))
                .perform(click());
        onView(allOf(withId(R.id.edit_text_topic), isDisplayed()))
                .perform(replaceText("Sujet"), closeSoftKeyboard());
        onView(allOf(withId(R.id.spinner), isDisplayed()))
                .perform(click());
        onData(anything()).atPosition(1)
                .perform(click());
        onView(allOf(withId(R.id.edit_text_reservation_name), isDisplayed()))
                .perform(replaceText("Simon"), closeSoftKeyboard());
        onView(allOf(withId(R.id.edit_text_date), isDisplayed()))
                .perform(click());
        onView(allOf(withId(android.R.id.button1), withText("OK")))
                .perform(scrollTo(), click());
        onView(allOf(withId(android.R.id.button1), withText("OK")))
                .perform(scrollTo(), click());
        onView(allOf(withId(R.id.edit_text_participantes), isDisplayed()))
                .perform(replaceText("simon@lamzon.com"), closeSoftKeyboard());
        onView(allOf(withId(R.id.validate_button), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.fragment_meeting_recyclerview), isDisplayed())).check(withItemCount(ITEMS_COUNT + 1));

    }

    /**
     * Test if I can delete an item
     */

    @Test
    public void deleteAnItem() {
        final int ITEMS_COUNT = apiService.getMeetings().size();
        onView(allOf(withId(R.id.fragment_meeting_recyclerview), isDisplayed())).check(withItemCount(ITEMS_COUNT));

        onView(allOf(withId(R.id.fragment_meeting_recyclerview), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

        onView(allOf(withId(R.id.fragment_meeting_recyclerview), isDisplayed())).check(withItemCount(ITEMS_COUNT - 1));

    }

    /**
     * Test if I can filter the list by room
     */

    @Test
    public void filterRecyclerViewByRoom() {
        onView(allOf(withContentDescription("menu"), isDisplayed()))
                .perform(click());
        onView(allOf(withId(R.id.title), withText("Sort by meeting room"), isDisplayed()))
                .perform(click());
        onView(allOf(withId(R.id.customPanel), isDisplayed()))
                .perform(click());
        onView(withText(containsString("Réunion B"))).inRoot(isPlatformPopup())
                .perform(click());
        onView(withId(R.id.roomName))
                .check(matches(withText("Réunion B")));

    }

    /**
     * Test if I can filter the list by date
     */

    @Test
    public void filterRecyclerViewByDate() {
        onView(allOf(ViewMatchers.withId(R.id.floatingActionButton), isDisplayed()))
                .perform(click());
        onView(allOf(withId(R.id.edit_text_topic), isDisplayed()))
                .perform(replaceText("Sujet"), closeSoftKeyboard());
        onView(allOf(withId(R.id.spinner), isDisplayed()))
                .perform(click());
        onData(anything()).atPosition(1)
                .perform(click());
        onView(allOf(withId(R.id.edit_text_reservation_name), isDisplayed()))
                .perform(replaceText("Simon"), closeSoftKeyboard());
        onView(allOf(withId(R.id.edit_text_date), isDisplayed()))
                .perform(click());
        onView(allOf(withId(android.R.id.button1), withText("OK")))
                .perform(scrollTo(), click());
        onView(allOf(withId(android.R.id.button1), withText("OK")))
                .perform(scrollTo(), click());
        onView(allOf(withId(R.id.edit_text_participantes), isDisplayed()))
                .perform(replaceText("simon@lamzon.com"), closeSoftKeyboard());
        onView(allOf(withId(R.id.validate_button), isDisplayed()))
                .perform(click());

        onView(allOf(withContentDescription("menu"), isDisplayed()))
                .perform(click());
        onView(allOf(withId(R.id.title), withText("Sort by date"), isDisplayed()))
                .perform(click());
        onView(allOf(withId(android.R.id.button1), withText("OK")))
                .perform(scrollTo(), click());
        onView(withId(R.id.roomName))
                .check(matches(withText("Réunion B")));


    }
}