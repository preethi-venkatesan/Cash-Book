package com.example.cashbook

import androidx.lifecycle.LiveData

class CashRepository(private var db: CashDatabase) {

    suspend fun insert(cashItems: CashItems) = db.getCashDao().insert(cashItems)

    suspend fun update(cashItems: CashItems) = db.getCashDao().updateData(cashItems.id,cashItems.notes,cashItems.amount,cashItems.cashInOrOut)

    suspend fun delete(id: Int)= db.getCashDao().delete(id)

    fun getAllItems(): LiveData<List<CashItems>> =
        db.getCashDao().getAllCashItems()

}