package com.example.twoapp

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_num_guss.*
import kotlin.random.Random

class NumGuss : AppCompatActivity() {
    private lateinit var guessField: EditText
    private lateinit var gButton: Button
    private lateinit var myLayout: ConstraintLayout
    private lateinit var theList: ArrayList<String>
    private lateinit var tvText: TextView

    private var rNumber = 0
    private var guessCount = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_num_guss)
        title = "Numbers Guss"
        rNumber = Random.nextInt(10)

        myLayout = findViewById(R.id.clMainnum)
        theList = ArrayList()

        tvText = findViewById(R.id.tvText)

        rvMessages.adapter = MessageAdapter(this, theList)
        rvMessages.layoutManager = LinearLayoutManager(this)

        guessField = findViewById(R.id.etGuessField)
        gButton = findViewById(R.id.btGuess)

        gButton.setOnClickListener { addGuss() }
    }


    //Menu Fun
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item: MenuItem = menu!!.getItem(1)
        if (item.title == "Other Game") {
            item.title = "Phrase Guss"
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.new_game -> {
                alertDialog(this, "New Game?")
                return true
            }
            R.id.Other_game -> {
                otherGame(PhGuss())
                return true
            }
            R.id.mi_main -> {
                otherGame(MainActivity()) }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun otherGame(activity: Activity){
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    private fun addGuss() {
        val input = guessField.text.toString()

        if (input.isNotEmpty()) {
            if (guessCount > 0) {
                if (input.toInt() == rNumber) {
                    Enable()
                    alertDialog(this, "Correct guess!! \n\n play again?")
                } else {
                    guessCount--
                    theList.add("Your guess $input is wrong")
                    theList.add("You have guessed $guessCount left")
                }
                if (guessCount == 0) {
                    Enable()
                    theList.add("The correct answer was $rNumber")
                    theList.add("Game Over")
                    //
                    alertDialog(
                        this,
                        "You lose...\nThe correct answer was $rNumber.\n\nPlay again?"
                    )
                }
            }
            guessField.text.clear()
            guessField.clearFocus()
            rvMessages.adapter?.notifyDataSetChanged()
        } else {
            Snackbar.make(myLayout, "Please enter a number", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun Enable() {
        gButton.isEnabled = false
        gButton.isClickable = false
        guessField.isEnabled = false
        guessField.isClickable = false
    }


}