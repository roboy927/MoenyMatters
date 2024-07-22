package com.kanishthika.moneymatters

import android.util.Log
import org.junit.Test
import java.time.LocalDate

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
       Log.d("TAG", "${LocalDate.now().year}")
    }
}