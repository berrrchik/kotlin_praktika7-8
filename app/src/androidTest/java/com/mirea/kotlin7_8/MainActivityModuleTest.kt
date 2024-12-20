package com.mirea.kotlin7_8

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityModuleTest {
    @Test
    fun testImageIsLoaded() {
        val network = NetworkUtilities()

        val imageUrl = "https://sun9-45.userapi.com/impg/X8C-byi53tOWi1GE3BcEXY__UmtnsCB4vj6OrA/GLu6jm61cXg.jpg?size=510x512&quality=95&sign=b2933b0f5ecc5db983c881c1557cfaba&type=album"
        val bitmapDeferred = network.downloadImage(imageUrl)

        runBlocking {
            val bitmap = bitmapDeferred.await()
            assertNotNull(bitmap)
        }
    }
}