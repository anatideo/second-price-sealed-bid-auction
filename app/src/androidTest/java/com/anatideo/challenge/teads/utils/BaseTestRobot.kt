package com.anatideo.challenge.teads.utils

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId

open class BaseTestRobot {
    fun textView(resId: Int): ViewInteraction = onView(withId(resId))

    fun matchText(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
        .check(ViewAssertions.matches(ViewMatchers.withText(text)))
}