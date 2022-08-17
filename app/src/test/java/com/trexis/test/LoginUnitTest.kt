package com.c1ctech.mvvmwithnetworksource

import com.c1ctech.mvvmwithnetworksource.ui.activities.LoginActivity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LoginUnitTest {

    @Test
    fun readStringFromContext_LocalizedString() {
        val loginTest = LoginActivity()

        // ...when the string is returned from the object under test...
        val result: String = loginTest.validate("morty", "smith")

        // ...then the result should be the expected one.
        assertThat(result, `is`(SUCCESSS))
    }

    companion object {
        private const val SUCCESSS = "Login was successful"
    }
}