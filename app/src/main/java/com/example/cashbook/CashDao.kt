package com.example.cashbook

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CashDao {

    @Query("SELECT * FROM CASHBOOK")
    fun getAllCashItems(): LiveData<List<CashItems>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cashItems: CashItems)

    @Query("DELETE FROM CASHBOOK WHERE id =:id")
    suspend fun delete(id: Int)

    @Query("UPDATE CASHBOOK SET notes=:notes, amount=:amount, cashInOrOut=:cashInOrOut WHERE id LIKE :id")
    suspend fun updateData(id: Int, notes: String, amount: Int, cashInOrOut: Int)

}