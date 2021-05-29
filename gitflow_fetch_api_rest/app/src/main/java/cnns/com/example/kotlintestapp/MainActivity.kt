package cnns.com.example.kotlintestapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class MainActivity : AppCompatActivity(), ExampleAdapter.OnItemClickListener {
    val exampleList = generateDummyList(25)
    val adapter = ExampleAdapter(exampleList, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        //Système de cache
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        val intent = intent
        val name = intent.getStringExtra("Name")
        val descriptionTexte = intent.getStringExtra("Description")

        if(name != null && descriptionTexte != null){
            val newItem = PokemonRecyclerView(
                name,
                descriptionTexte,
                0
            )
            adapter.notifyItemInserted(0)
            val db = DataBaseHandler(this)
            db.insertData(newItem)
        }

        generateDummyList(25)
        adapter.notifyDataSetChanged()

    }

    public override fun onStart() {

        super.onStart()
        generateDummyList(25)
                val newItem = PokemonRecyclerView(
            "Base de pokemon",
            "Clickez pour créer de nouveaux pokémons",
            0
        )
        exampleList.add(0, newItem)
        adapter.notifyItemInserted(0)
        adapter.notifyDataSetChanged()
    }

    public override fun onResume() {

        super.onResume()
        generateDummyList(25)
        adapter.notifyDataSetChanged()
    }


//    fun removeItem(view: View) {
//        val index = Random.nextInt(8)
//        exampleList.removeAt(index)
//        adapter.notifyItemRemoved(index)
//    }
    override fun onItemClick(position: Int) {
        Toast.makeText(this, "L'élément $position a été cliqué", Toast.LENGTH_SHORT).show()
        val clickedItem = exampleList[position]
        adapter.notifyItemChanged(position)
        val intent = Intent(this, DetailElement::class.java)
        intent.putExtra("Name", clickedItem.text1)
        intent.putExtra("Description", clickedItem.text2)

        startActivity(intent)
    }

    fun insertItemPageOpen(view: View) {
//        val index = Random.nextInt(8)
//
//        val newItem = PokemonRecyclerView(
//            "Cycle de vie",
//            "Passage sur resume",
//            0
//        )
//        exampleList.add(index, newItem)
//        adapter.notifyItemInserted(index)
        val intent = Intent(this, AddingElement::class.java)
        startActivity(intent)
    }

     fun generateDummyList(size: Int): ArrayList<PokemonRecyclerView> {
         val context = this
         val db = DataBaseHandler(context)
         val list = ArrayList<PokemonRecyclerView>()

         title = "KotlinApp"
//         val item = PokemonRecyclerView("Item"," content?.title", 0)

             for ( i in 0 until 25) {
                 GlobalScope.launch(Dispatchers.Main) {
//                     println("db.readData(25).size" + db.readData(25).size)
//                     println(db.readData(25).size)
                     try {
                         if(db.readData(25).size < 3){

                             val response = ApiClient.apiService.getPokemontById(i)
                             if (response.isSuccessful && response.body() != null) {
                                 val content = response.body()
                                 //                        println(content)
                                 val item = PokemonRecyclerView(content?.name, content?.weight, content?.id)
                                 list += item
                                 db.insertData(item)
                                 //do something
                             } else {
                                 //                        Toast.makeText(
                                 //                            this@MainActivity,
                                 //                            "Error Occurred: ${response.message()}",
                                 //                            Toast.LENGTH_LONG
                                 //                        ).show()
                             }
                         } else {
                             val a = db.readData(size-1).get(i)
                             val item = PokemonRecyclerView( a.text1, a.text2, a.id)
                             list += item
                         }

                     } catch (e: Exception) {
                         //                    Toast.makeText(
                         //                        this@MainActivity,
                         //                        "Error Occurred: ${e.message}",
                         //                        Toast.LENGTH_LONG
                         //                    ).show()
                     }
                 }
         }

        return list
    }

}