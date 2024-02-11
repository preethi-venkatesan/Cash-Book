package com.example.cashbook

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(), MyAdapter.OnClickItemInterface {


    private lateinit var btnCashIn: Button
    private lateinit var btnCashOut: Button
    private lateinit var tvCashIn: TextView
    private lateinit var tvCashOut: TextView
    private lateinit var tvBalance: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var cashViewModel: CashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCashIn = findViewById(R.id.btnCashIn)
        btnCashOut = findViewById(R.id.btnCashOut)
        recyclerView = findViewById(R.id.recyclerView)
        tvCashIn = findViewById(R.id.tvCashIn)
        tvCashOut = findViewById(R.id.tvCashOut)
        tvBalance = findViewById(R.id.tvBalance)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val cashRepository = CashRepository(CashDatabase.getDatabase(this))
        val factory = CashViewModelFactory(cashRepository)
        cashViewModel = ViewModelProvider(this, factory).get(CashViewModel::class.java)


        btnCashIn.setOnClickListener {


            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("cashInOrOut", 1)
            startActivity(intent)
        }
        btnCashOut.setOnClickListener {

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("cashInOrOut", 2)
            startActivity(intent)
        }

        cashViewModel.getAllMoneyItems().observe(this, Observer {

            myAdapter = MyAdapter(this, it, this)
            recyclerView.adapter = myAdapter
            myAdapter.notifyDataSetChanged()


            val totalCashIn = it.filter { it.cashInOrOut == 1 }.sumBy { it.amount }
            tvCashIn.text = "$totalCashIn"

            val totalCashOut = it.filter { it.cashInOrOut == 2 }.sumBy { it.amount }
            tvCashOut.text = "$totalCashOut"

            val totalBalance = totalCashIn - totalCashOut
            tvBalance.text = "$totalBalance"

        })

    }

    override fun onClickItem(cashItems: CashItems) {
        val intent = Intent(this, ExitDelete::class.java)
        intent.putExtra("id", cashItems.id)
        intent.putExtra("notes", cashItems.notes)
        intent.putExtra("amount", cashItems.amount)
        intent.putExtra("cashInOrOut", cashItems.cashInOrOut)
        startActivity(intent)
        println("onClick")
    }

}