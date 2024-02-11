package com.example.cashbook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity ( tableName = "CASHBOOK")
data class CashItems(
    @PrimaryKey(autoGenerate = true) var id: Int =0,
    @ColumnInfo(name = "notes") var notes : String = "",
    @ColumnInfo(name = "amount") var amount :Int = 0,
    @ColumnInfo(name = "cashInOrOut") var cashInOrOut : Int = 0
)

