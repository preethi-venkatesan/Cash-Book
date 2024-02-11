package com.example.cashbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip

class ExitDelete : AppCompatActivity() {

    private lateinit var etCash: EditText
    private lateinit var notes: EditText
    private lateinit var btnDelete: Button
    private lateinit var btnSave: Button
    private lateinit var cashInChip: Chip
    private lateinit var cashOutChip: Chip
    private lateinit var cashViewModel: CashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exit_delete)

        println("onCreate")

        etCash = findViewById(R.id.etCash)
        notes = findViewById(R.id.notes)
        btnDelete = findViewById(R.id.deleteBtn)
        btnSave = findViewById(R.id.btnSave)
        cashInChip = findViewById(R.id.cashIn)
        cashOutChip = findViewById(R.id.cashOut)

        val cashRepository = CashRepository(CashDatabase.getDatabase(this))
        val factory = CashViewModelFactory(cashRepository)
        cashViewModel = ViewModelProvider(this, factory).get(CashViewModel::class.java)

        println("hello")
        var id = intent.getIntExtra("id",0)
        var notesReceived = intent.getStringExtra("notes")
        var amountReceived = intent.getIntExtra("amount", 0)
        var cashInOrOut = intent.getIntExtra("cashInOrOut", 0)

        println("dataReceived")

        if (cashInOrOut == 1) {
            cashInChip.isChecked = true
            etCash.setText(amountReceived.toString())
            notes.setText(notesReceived)
            cashInChip.setChipBackgroundColorResource(R.color.green1)
            etCash.setHintTextColor(ContextCompat.getColor(this, R.color.green1))
        } else if (cashInOrOut == 2) {
            cashOutChip.isChecked = true
            etCash.setText(amountReceived.toString())
            notes.setText(notesReceived)
            cashOutChip.setChipBackgroundColorResource(R.color.Orange)
            etCash.setHintTextColor(ContextCompat.getColor(this, R.color.Orange))
        }
        cashInChip.setOnClickListener {
            cashInOrOut = 1
            etCash.setText(amountReceived.toString())
            notes.setText(notesReceived)
            cashOutChip.setChipBackgroundColorResource(R.color.grey)
            etCash.setHintTextColor(ContextCompat.getColor(this, R.color.green1))
            cashInChip.setChipBackgroundColorResource(R.color.green1)
        }

        cashOutChip.setOnClickListener {
            cashInOrOut = 2
            etCash.setText(amountReceived.toString())
            notes.setText(notesReceived)
            cashInChip.setChipBackgroundColorResource(R.color.grey)
            etCash.setHintTextColor(ContextCompat.getColor(this, R.color.Orange))
            cashOutChip.setChipBackgroundColorResource(R.color.Orange)
        }

        btnSave.setOnClickListener {

            val cash: String = etCash.text.toString()
            val note: String = notes.text.toString()

            if (cash.isBlank() || note.isBlank()) {

                Toast.makeText(
                    this,
                    "Please fill the required fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                val amount: Int = cash.toInt()

                val cashItems = CashItems(id, note, amount, cashInOrOut)
                cashViewModel.update(cashItems)
                Toast.makeText(this, "Updated!!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnDelete.setOnClickListener {


            val cash: String = etCash.text.toString()
            val note: String = notes.text.toString()

            if (cash.isBlank() || note.isBlank()) {

                Toast.makeText(
                    this,
                    "Please fill the required fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                cashViewModel.delete(id)
                Toast.makeText(this, "Deleted!!", Toast.LENGTH_SHORT).show()
                finish()
            }

        }

    }

}