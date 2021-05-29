package cnns.com.example.kotlintestapp


import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class TestFetching {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    //Fetch le premier Pokmon pour s'assurer que l'API est bonne
    @Test
    fun fetchById_first() = runBlocking {
        var list = ArrayList<PokemonRecyclerView>()
        var checkIsSuperior = false
        launch(Dispatchers.Main) {
            try {
                    val response = ApiClient.apiService.getPokemontById(1)
                    if (response.isSuccessful && response.body() != null) {
                        val content = response.body()
                        val item = PokemonRecyclerView(content?.name, content?.weight, content?.id)
                        list.add(item)

                        //do something
                    }

                } catch (e: Exception) {
                    // e
            }
            print(list.size)
            //on s'assure d'avoir fetché au moins un élément
            if(list.size > 0 ){
                checkIsSuperior = true
            }
            assertEquals(true, checkIsSuperior)

        }
        val FIN_TEST_HOOKER = 1 // pas utilisé
    }
}