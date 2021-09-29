package com.example.twoapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_num_guss.*
import kotlinx.android.synthetic.main.activity_ph_guss.*

class PhGuss : AppCompatActivity() {
    private lateinit var myLayout: ConstraintLayout
    private lateinit var guessInput: EditText
    private lateinit var guessButton: Button
    private lateinit var theList: ArrayList<String>
    private lateinit var textPhrase: TextView
    private lateinit var textLettr: TextView

    private val answer = "to infinity and beyond"
    private val mapOfAnswer = mutableMapOf<Int, Char>()
    private var theAnswer = ""
    private var guessLettr = ""
    private var countG = 0
    private var checkG = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ph_guss)

        title="Phrase Guss"
        for (a in answer.indices) {
            if (answer[a] == ' ') {
                mapOfAnswer[a] = ' '
                theAnswer += ' '
            } else {
                mapOfAnswer[a] = '*'
                theAnswer += '*'
            }
        }

        theList = ArrayList()
        myLayout = findViewById(R.id.clMainph)

        rvPH.adapter = MessageAdapter(this, theList)
        rvPH.layoutManager = LinearLayoutManager(this)


        textPhrase=findViewById(R.id.textPhrase)
        textLettr=findViewById(R.id.textLettr)
        guessInput = findViewById(R.id.etGuess)
        guessButton = findViewById(R.id.btGuess)
        guessButton.setOnClickListener { addGuss() }

        changeText()
    }

    //Menue Fun
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item: MenuItem = menu!!.getItem(1)
        if (item.title == "Other Game") {
            item.title = "Numbers Guss"
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
                otherGame(NumGuss())
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


    fun addGuss() {
        val input = guessInput.text.toString()
        if (checkG) {
            if (input == answer) {
                disableEntry()
                alertDialog(this,"Correct! \n Again?")
            } else {
                theList.add("$input is wrong")
                checkG = false
                changeText()
            }
        } else {
            if (input.isNotEmpty() && input.length == 1) {
                theAnswer = ""
                checkG = true
                checkChar(input[0])
            } else {
                Snackbar.make(clMainph, "enter one character please", Snackbar.LENGTH_LONG).show()
            }
        }
        guessInput.text.clear()
        guessInput.clearFocus()
        rvPH.adapter?.notifyDataSetChanged()
    }

    private fun disableEntry() {
        guessButton.isEnabled = false
        guessButton.isClickable = false
        guessInput.isEnabled = false
        guessInput.isClickable = false
    }

    private fun checkChar(guessC: Char) {
        var found = 0
        for (a in answer.indices) {
            if (answer[a] == guessC) {
                mapOfAnswer[a] = guessC
                found++
            }
        }
        for (a in mapOfAnswer) {
            theAnswer += mapOfAnswer[a.key]
        }
        if (theAnswer == answer) {
            disableEntry()
            alertDialog(this,"Correct Guess! \n Again?")
        }
        if (guessLettr.isEmpty()) {
            guessLettr +=guessC
        }else{
            guessLettr+= ",, $guessC"
        }
        if(found>0){
            theList.add("Found $found ${guessC.toUpperCase()}(s)")
        }
        else{
            theList.add("No $found ${guessC.toUpperCase()}(s)")
        }
        countG++
        val guessesLeft = 10 - countG
        if(countG<10){theList.add("$guessesLeft guesses remaining")}
        changeText()
        rvPH.scrollToPosition(theList.size - 1)    }

    private fun changeText() {
        textPhrase.text = ("Phrase:  " + theAnswer.toUpperCase()).toString()

        textLettr.text = ("Letters Guessed: $guessLettr").toString()
        if (checkG) {
            guessInput.hint = "Try to guess the phrase"
        } else {
            guessInput.hint = "Try to guess a character"
        }
    }
}