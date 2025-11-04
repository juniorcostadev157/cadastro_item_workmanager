package com.junior.projetomvvmcleanxml

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.junior.projetomvvmcleanxml.presentation.principal.list_item_room_fragment.ListRoomFragmentFake
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListRoomFragmentUITest {

    @Test
    fun shouldRenderBaseUIElements() {
        val scenario = launchFragmentInContainer<ListRoomFragmentFake>(
            themeResId = R.style.Theme_ProjetoMVVMCleanXML
        )
        scenario.onFragment { Thread.sleep(200) }

        onView(withId(R.id.txtListRoom)).check(matches(isDisplayed()))
        onView(withId(R.id.switchSync)).check(matches(isDisplayed()))
    }
}