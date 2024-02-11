package com.example.cashbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CashViewModelFactory (private var cashRepository: CashRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CashViewModel(cashRepository) as T
    }
}