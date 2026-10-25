package com.pynexia.week1.bmi;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.pm.ActivityInfo;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AppFlowTest {
    private void snapshot(String name) throws java.io.IOException {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        android.graphics.Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
        java.io.File folder = new java.io.File(InstrumentationRegistry.getInstrumentation().getTargetContext().getExternalFilesDir(null), "evidence");
        if (!folder.exists() && !folder.mkdirs()) throw new java.io.IOException("Cannot create screenshot folder");
        try (java.io.FileOutputStream stream = new java.io.FileOutputStream(new java.io.File(folder, name + ".png"))) {
            if (!bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, stream)) throw new java.io.IOException("Cannot save screenshot");
        } finally { bitmap.recycle(); }
    }

    private Activity launch(Class<? extends Activity> type) {
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return InstrumentationRegistry.getInstrumentation().startActivitySync(intent);
    }

    @Test public void calculatesRejectsAndResets() throws Exception {
        launch(MainActivity.class);
        onView(withId(R.id.calculate)).perform(scrollTo(), click());
        onView(withId(R.id.height)).check(matches(hasErrorText("Enter a valid height from 50 to 300 cm.")));
        onView(withId(R.id.height)).perform(replaceText("175"), closeSoftKeyboard());
        onView(withId(R.id.weight)).perform(replaceText("70"), closeSoftKeyboard());
        onView(withId(R.id.calculate)).perform(scrollTo(), click());
        onView(withId(R.id.result)).check(matches(withText("22.86")));
        onView(withId(R.id.category)).check(matches(withText("Normal")));
        onView(withText(R.string.eyebrow)).perform(scrollTo());
        snapshot("bmi-result");
        onView(withId(R.id.weight)).perform(scrollTo(), replaceText("90"), closeSoftKeyboard());
        onView(withId(R.id.result)).check(matches(withText("Your result will appear here")));
        onView(withId(R.id.calculate)).perform(scrollTo(), click());
        onView(withId(R.id.category)).check(matches(withText("Overweight")));
        onView(withId(R.id.reset)).perform(scrollTo(), click());
        onView(withId(R.id.height)).check(matches(withText("")));
        onView(withId(R.id.weight)).check(matches(withText("")));
    }
    @Test public void restoresCalculatedResultAfterRecreation() {
        Activity activity = launch(MainActivity.class);
        onView(withId(R.id.height)).perform(replaceText("175"), closeSoftKeyboard());
        onView(withId(R.id.weight)).perform(replaceText("70"), closeSoftKeyboard());
        onView(withId(R.id.calculate)).perform(scrollTo(), click());
        InstrumentationRegistry.getInstrumentation().runOnMainSync(activity::recreate);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        onView(withId(R.id.result)).check(matches(withText("22.86")));
    }

}
