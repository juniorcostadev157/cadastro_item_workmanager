package com.junior.projetomvvmcleanxml

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.junior.projetomvvmcleanxml.presentation.principal.list_item_room_fragment.CreateItemFragmentFake
import org.hamcrest.core.IsNot.not

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateItemFragmentUITest {

    @Test
    fun shouldShowLoading_whenClickButton() {
        // Lança o fragmento fake
        launchFragmentInContainer<CreateItemFragmentFake>(
            themeResId = R.style.Theme_ProjetoMVVMCleanXML
        )

        // Clica no botão
        onView(withId(R.id.btn_criar)).perform(click())

        // Verifica se o loading apareceu
        onView(withId(R.id.progress_loading_item))
            .check(matches(isDisplayed()))

        // Verifica se o botão ficou desabilitado
        onView(withId(R.id.btn_criar))
            .check(matches(not(isEnabled())))
    }
}
