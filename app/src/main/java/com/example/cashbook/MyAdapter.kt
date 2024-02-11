package com.example.cashbook

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    var context: Activity,
    private var list: List<CashItems>,
    private var onClickItemInterface: OnClickItemInterface
) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    interface OnClickItemInterface {

        fun onClickItem(cashItems: CashItems)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvItem = itemView.findViewById<TextView>(R.id.textView)
        var tvAmtIn = itemView.findViewById<TextView>(R.id.textView2)
        var tvAmtOut = itemView.findViewById<TextView>(R.id.textView3)
        var layout = itemView.findViewById<CardView>(R.id.layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.tvItem.text = currentItem.notes
        if (currentItem.cashInOrOut == 1) {
            holder.tvAmtIn.visibility = View.VISIBLE // Set visibility to VISIBLE
            holder.tvAmtOut.visibility = View.GONE
            holder.tvAmtIn.text = currentItem.amount.toString()

        } else if (currentItem.cashInOrOut == 2) {
            holder.tvAmtOut.visibility = View.VISIBLE // Set visibility to VISIBLE
            holder.tvAmtIn.visibility = View.GONE
            holder.tvAmtOut.text = currentItem.amount.toString()
        }
        holder.layout.setOnClickListener {
            val cashItems =CashItems(currentItem.id,currentItem.notes,currentItem.amount,currentItem.cashInOrOut)
             onClickItemInterface.onClickItem(cashItems)
        }

    }
}