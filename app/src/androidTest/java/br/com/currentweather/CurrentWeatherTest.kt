package br.com.currentweather

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.currentweather.commom.idling.ApiIdlingResource
import br.com.currentweather.ui.views.MainActivity
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrentWeatherTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(ApiIdlingResource.getIdlingResource())
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(ApiIdlingResource.getIdlingResource())
    }

    @Test
    fun testCurrentWeather() {
        onView(withId(R.id.text_field)).perform(typeText("Teresina"))

        onView(
            allOf(
                isAssignableFrom(TextInputLayout::class.java),
                withId(R.id.text_input_layout),
                TextInputLayoutMatchers.withEndIconDrawable(android.R.drawable.ic_menu_search)
            )
        ).perform(click())

        onView(withId(R.id.city_name)).check(matches(withText("Teresina")))
    }
}
