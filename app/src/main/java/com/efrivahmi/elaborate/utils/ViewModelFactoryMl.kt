package com.efrivahmi.elaborate.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.efrivahmi.elaborate.data.api.ml.DataSourDiagnose
import com.efrivahmi.elaborate.ui.main.diagnose.DiagnoseViewModel
import java.lang.IllegalArgumentException

class ViewModelFactoryMl(private val pref: DataSourDiagnose) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DiagnoseViewModel::class.java) -> {
                DiagnoseViewModel(pref) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryMl? = null
        fun getInstance(context: Context): ViewModelFactoryMl {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactoryMl(Injection.labDiagnose(context))
            }.also { instance = it }
        }
    }
}