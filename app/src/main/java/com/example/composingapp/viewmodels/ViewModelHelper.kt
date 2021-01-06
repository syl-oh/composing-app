package com.example.composingapp.viewmodels

import androidx.lifecycle.MutableLiveData

object ViewModelHelper {
    /**
     * Thanks to DVegasa at https://stackoverflow.com/questions/48020377/livedata-update-on-object-field-change
     * for providing a solution to notify observers of MutableLiveData when a property of the object
     * changes
     */
    fun <T> MutableLiveData<T>.mutation(actions: (MutableLiveData<T>) -> Unit) {
        actions(this)
        this.value = this.value
    }
}