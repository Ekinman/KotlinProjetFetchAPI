package cnns.com.example.kotlintestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_adding_element.*
import kotlin.random.Random

class AddingElement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_element)

        val actionBar = supportActionBar

        actionBar!!.title = "Ajout d'un pokémon"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    fun insertItem(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        var editPokemonName = editPokemonName.text.toString()
        var editPokemonWeight = editPokemonWeight.text.toString()

        intent.putExtra("Name", editPokemonName)
        intent.putExtra("Description", editPokemonWeight)
        startActivity(intent)
    }
}