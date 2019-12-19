package com.example.android.architecture.blueprints.todoapp.test.chapter1.datainteraction;

import android.preference.PreferenceActivity;
import android.support.test.espresso.DataInteraction;
import android.view.View;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.test.BaseTest;

import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.Map;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.PreferenceMatchers.withKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withTagKey;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.android.architecture.blueprints.todoapp.test.helpers.CommonElements.openDrawer;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;


/**
 * DataInteraction samples.
 */
public class DataInteractionsTest extends BaseTest {

    @Test
    public void dataInteractionSample() {
        openDrawer();
        onView(allOf(withId(R.id.design_menu_item_text),
                withText(R.string.settings_title))).perform(click());

        onData(anything())
                .inAdapterView(withId(android.R.id.list))   //Id de la lista
                .atPosition(0)                              // Posicion del elemento a seleccionar
                .onChildView(withId(android.R.id.title))    // va a una vista interior a buscar ese ID
                .check(matches(withText("General")))        // Valida si  ese objeto contiene el texto "General"
                .perform(click());                          // Realiza un click
        onData(withKey("email_edit_text"))                  // Esta informacion esta ubicada en res/xml/pref_general.xml
                /*we have to point explicitly what is the parent of of the General prefs list
                because there are two {@ListView}s with the same id - android.R.id.list in the hierarchy*/
                .inAdapterView(allOf(withId(android.R.id.list), withParent(withId(android.R.id.list_container))))
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withId(android.R.id.edit)).perform(replaceText("sample@ema.il"));
        onView(withId(android.R.id.button1)).perform(click());

        // Verify new email is shown.
        onData(withKey("email_edit_text"))
                .inAdapterView(allOf(withId(android.R.id.list), withParent(withId(android.R.id.list_container))))
                .onChildView(withId(android.R.id.summary))
                .check(matches(withText("sample@ema.il")));
    }
}
