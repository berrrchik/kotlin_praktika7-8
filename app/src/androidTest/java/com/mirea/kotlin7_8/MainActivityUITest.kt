package com.mirea.kotlin7_8

import android.os.Handler
import android.os.Looper
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mirea.kotlin7_8.MainActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkUiElementsVisibility() {
        onView(withId(R.id.editTextUrl)).check(matches(isDisplayed()))
        onView(withId(R.id.buttonDownload)).check(matches(isDisplayed()))
    }

    @Test
    fun checkEditTextInitialState() {
        onView(withId(R.id.editTextUrl)).check(matches(withText("")))
    }

    @Test
    fun checkButtonText() {
        onView(withId(R.id.buttonDownload)).check(matches(withText("Загрузить изображение")))
    }

    @Test
    fun checkButtonClick() {
        onView(withId(R.id.editTextUrl)).perform(
            typeText("https://example.com/invalid.jpg"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.buttonDownload)).perform(click())

        Handler(Looper.getMainLooper()).postDelayed({
            activityRule.scenario.onActivity { activity ->
                onView(withText("Ошибка загрузки изображения"))
                    .inRoot(RootMatchers.withDecorView(not(activity.window.decorView)))
                    .check(matches(isDisplayed()))
            }
        }, 1000)
    }

    @Test
    fun checkImageViewDisplayAfterDownload() {
        onView(withId(R.id.editTextUrl)).perform(
            typeText("https://sun9-45.userapi.com/impg/X8C-byi53tOWi1GE3BcEXY__UmtnsCB4vj6OrA/GLu6jm61cXg.jpg?size=510x512&quality=95&sign=b2933b0f5ecc5db983c881c1557cfaba&type=album"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.buttonDownload)).perform(click())

        Handler(Looper.getMainLooper()).postDelayed({
            onView(withId(R.id.imageView)).check(matches(isDisplayed()))
        }, 3000)
    }

}