package com.junior.projetomvvmcleanxml

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.junior.projetomvvmcleanxml.presentation.login.LoginActivity
import org.hamcrest.core.IsNot.not
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginActivityTest {



    @Before
    fun setup() {

        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun deveMostrarCamposCorretamente() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.et_email)).check(matches(isDisplayed()))
        onView(withId(R.id.et_password)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
    }

    @Test
    fun quandoCamposEstaoVazios_mostraToast() {
        ActivityScenario.launch(LoginActivity::class.java)
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.btn_login)).check(matches(isEnabled()))
    }

    @Test
    fun quandoPreencheCampos_botaoDesabilitaDuranteLoading() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.et_email)).perform(typeText("teste@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.et_password)).perform(typeText("123456"), closeSoftKeyboard())

        // Clica pra simular o login
        onView(withId(R.id.btn_login)).perform(click())

        // Verifica se o botão desabilita e o loading aparece
        onView(withId(R.id.btn_login)).check(matches(not(isEnabled())))
        onView(withId(R.id.progress_loading)).check(matches(isDisplayed()))
    }


}
