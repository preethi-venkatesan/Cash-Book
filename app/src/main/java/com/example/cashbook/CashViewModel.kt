package com.example.cashbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CashViewModel(private var cashRepository: CashRepository) : ViewModel() {

    fun insert(cashItems: CashItems) = GlobalScope.launch {

        cashRepository.insert(cashItems)
    }

    fun update(cashItems: CashItems) = GlobalScope.launch {

            cashRepository.update(cashItems)
        }
    fun delete(id: Int) = viewModelScope.launch {

        cashRepository.delete(id)
    }


    fun getAllMoneyItems(): LiveData<List<CashItems>> {

        return cashRepository.getAllItems()

    }
}