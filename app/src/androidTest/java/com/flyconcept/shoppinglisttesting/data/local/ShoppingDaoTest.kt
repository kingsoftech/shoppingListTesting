package com.flyconcept.shoppinglisttesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.flyconcept.shoppinglisttesting.data.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest() {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: ShoppingItemDatabase
    private lateinit var  dao:ShoppingDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.shoppingDao()
    }
    @After

    fun teardown(){
        database.close()
    }


    @Test
    fun insertShoppingItem()  = runTest {
        val shoppingItem  =ShoppingItem("bananna", 1, 1f, "irdjvvsavllvka", 1)
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItem().getOrAwaitValue()

        assertThat(allShoppingItem).contains(shoppingItem)

    }

    @Test

    fun deleteShoppingItem() = runTest {
        val shoppingItem  =ShoppingItem("bananna", 1, 1f, "irdjvvsavllvka", 1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItem().getOrAwaitValue()

        assertThat(allShoppingItem).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPriceSum() =  runTest {
        val shoppingItem  =ShoppingItem("bananna", 2, 10f, "irdjvvsavllvka", 1)
        val shoppingItem1  =ShoppingItem("bananna", 3, 5.5f, "irdjvvsavllvka", 2)
        val shoppingItem2  =ShoppingItem("bananna", 0, 100f, "irdjvvsavllvka", 3)
        dao.insertShoppingItem(shoppingItem)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)

        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo(2*10f + 3*5.5f)
    }
}