package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import android.util.Xml
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    enum class Turn
    {
        O,
        X
    }

    private var firstTurn = Turn.X
    private var currentTurn = Turn.X

    private var boardList = mutableListOf<Button>()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initBoard()
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view: View) {
        if(view !is Button)
            return
        addToBoard(view)
    }

    private fun addToBoard(button: Button) {
        if(button.text != "")
            return

        if(currentTurn == Turn.O){
            button.text = O
            currentTurn = Turn.X
        } else if(currentTurn == Turn.X){
            button.text = X
            currentTurn = Turn.O
        }
        setTurnLabel()
    }

    private fun setTurnLabel() {
        var turnText = ""
        if(currentTurn == Turn.X)
            turnText = "Turn $X"
        else if(currentTurn == Turn.O)
            turnText = "Turn $O"
        binding.turnTV.text = turnText
    }

    companion object {
        const val O = "O"
        const val X = "X"
    }
}