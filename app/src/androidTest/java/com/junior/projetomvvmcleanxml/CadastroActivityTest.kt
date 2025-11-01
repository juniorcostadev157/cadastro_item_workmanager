package com.junior.projetomvvmcleanxml


import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.junior.projetomvvmcleanxml.presentation.cadastro.CadastroActivity
import junit.framework.TestCase.assertTrue
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CadastroActivityTest {

    @Before
    fun setup(){
        Intents.init()
    }

    @After
    fun tearDown(){
        Intents.release()
    }


    @Test
    fun when_the_fields_are_empty_show_the_message_Toast(){
        ActivityScenario.launch(CadastroActivity::class.java)
        onView(withId(R.id.btn_cadastrar)).perform(click())

    }

    @Test
    fun whenFillFieldsButtonDisableDuringLogin(){
        ActivityScenario.launch(CadastroActivity::class.java)
        onView(withId(R.id.et_nome_cadastro)).perform(typeText("teste"))
        onView(withId(R.id.et_email_cadastro)).perform(typeText("teste@teste.com"))
        onView(withId(R.id.et_password_cadastro)).perform(typeText("123456"))
        onView(withId(R.id.et_confirm_password_cadastro)).perform(typeText("123456"), closeSoftKeyboard())

        onView(withId(R.id.btn_cadastrar)).perform(click())

        onView(withId(R.id.btn_cadastrar)).check(matches(not(isEnabled())))
        onView(withId(R.id.progress_loading_cadastro)).check(matches(isDisplayed()))



    }

    @Test
    fun whenShowToastMessageIfPasswordDifferent() {
        ActivityScenario.launch(CadastroActivity::class.java)

        onView(withId(R.id.et_nome_cadastro)).perform(typeText("teste"))
        onView(withId(R.id.et_email_cadastro)).perform(typeText("teste@teste.com"))
        onView(withId(R.id.et_password_cadastro)).perform(typeText("123456"))
        onView(withId(R.id.et_confirm_password_cadastro))
            .perform(typeText("123456-"), closeSoftKeyboard())

        onView(withId(R.id.btn_cadastrar)).perform(click())


    }
    @Test
    fun whenClickBackButton_shouldNavigateToLoginActivity(){
         val scenario = ActivityScenario.launch(CadastroActivity::class.java)

        onView(withId(R.id.ib_back_cadastro)).perform(click())

       assertTrue(scenario.state.isAtLeast(Lifecycle.State.DESTROYED))
    }


}