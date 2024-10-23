package br.com.currentweather

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Matcher

object TextInputLayoutMatchers {

    fun withEndIconDrawable(resourceId: Int): Matcher<View> {

        // Returns an instance of a hamcrest matcher for a specific type of view
        return object : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {

            // Method for the matcher to describe itself
            override fun describeTo(description: org.hamcrest.Description?) {
                description?.appendText("with end icon drawable from resource id: ")
                description?.appendValue(resourceId)
            }

            // Method for the matcher to check if the view matches or not
            override fun matchesSafely(textInputLayout: TextInputLayout): Boolean {
                return textInputLayout.endIconDrawable?.constantState == textInputLayout.context.resources.getDrawable(
                    resourceId,
                    null
                )?.constantState
            }
        }
    }
}
