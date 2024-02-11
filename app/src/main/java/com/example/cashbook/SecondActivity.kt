package com.example.cashbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText

class SecondActivity : AppCompatActivity() {

    private lateinit var etCash: EditText
    private lateinit var notes: EditText
    private lateinit var btnSaveAndExit: Button
    private lateinit var btnSaveAndContinue: Button
    private lateinit var cashInChip: Chip
    private lateinit var cashOutChip: Chip
    private lateinit var cashViewModel: CashViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        etCash = findViewById(R.id.etCash)
        notes = findViewById(R.id.notes)
        btnSaveAndExit = findViewById(R.id.btnSaveAndExit)
        btnSaveAndContinue = findViewById(R.id.btnSaveAndContinue)
        cashInChip = findViewById(R.id.cashIn)
        cashOutChip = findViewById(R.id.cashOut)

        val cashRepository = CashRepository(CashDatabase.getDatabase(this))
        val factory = CashViewModelFactory(cashRepository)
        cashViewModel = ViewModelProvider(this, factory).get(CashViewModel::class.java)


        var cashInOrOut = intent.getIntExtra("cashInOrOut", 0)

        if (cashInOrOut == 1) {
            cashInChip.isChecked = true
            cashInChip.setChipBackgroundColorResource(R.color.green1)
            etCash.setHint("Cash In")
            etCash.setHintTextColor(ContextCompat.getColor(this, R.color.green1))

        } else if (cashInOrOut == 2) {
            cashOutChip.isChecked = true
            cashOutChip.setChipBackgroundColorResource(R.color.Orange)
            etCash.setHint("Cash Out")
            etCash.setHintTextColor(ContextCompat.getColor(this, R.color.Orange))
        }


        cashInChip.setOnClickListener {
            cashInOrOut = 1
            etCash.setHint("Cash In")
            cashOutChip.setChipBackgroundColorResource(R.color.grey)
            etCash.setHintTextColor(ContextCompat.getColor(this, R.color.green1))
            cashInChip.setChipBackgroundColorResource(R.color.green1)
        }

        cashOutChip.setOnClickListener {
            cashInOrOut = 2
            etCash.hint = "Cash Out"
            cashInChip.setChipBackgroundColorResource(R.color.grey)
            etCash.setHintTextColor(ContextCompat.getColor(this, R.color.Orange))
            cashOutChip.setChipBackgroundColorResource(R.color.Orange)
        }

        btnSaveAndExit.setOnClickListener {

            val cashEntered: String = etCash.text.toString()
            val note: String = notes.text.toString()

            if (cashEntered.isBlank() || note.isBlank()) {

                Toast.makeText(
                    this,
                    "Please fill the required fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                val amount: Int = cashEntered.toInt()

                val cashItems = CashItems(0, note, amount, cashInOrOut)
                cashViewModel.insert(cashItems)
                Toast.makeText(this, "Inserted!!", Toast.LENGTH_SHORT).show()
                etCash.text.clear()
                notes.text.clear()
                finish()
            }
        }


        btnSaveAndContinue.setOnClickListener {

            val cashEntered: String = etCash.text.toString()
            val note: String = notes.text.toString()


            if (cashEntered.isNullOrBlank() || note.isNullOrBlank()) {

                Toast.makeText(
                    this,
                    "Please fill the required fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val amount: Int = cashEntered.toInt()

                val cashItems = CashItems(0, note, amount, cashInOrOut)
                cashViewModel.insert(cashItems)
                Toast.makeText(this, "Inserted!!", Toast.LENGTH_SHORT).show()
                etCash.text.clear()
                notes.text.clear()
            }

        }


    }
}


