package com.example.twoapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var numButton: Button
    private lateinit var phraseButton: Button
    private lateinit var myLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title="2 in 1 App"
        myLayout = findViewById(R.id.clMain)

        numButton = findViewById(R.id.Numbutton)
        phraseButton = findViewById(R.id.Phrasebutton)

        numButton.setOnClickListener {
            goGame(NumGuss())
        }

        phraseButton.setOnClickListener {
            goGame(PhGuss())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.num_game -> {
                goGame(NumGuss())
                return true
            }
            R.id.phrass_guss -> {
                goGame(PhGuss())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goGame(activity : Activity){
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

}