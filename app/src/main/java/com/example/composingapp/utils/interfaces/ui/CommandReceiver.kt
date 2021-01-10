package com.example.composingapp.utils.interfaces.ui

interface CommandReceiver {
    fun actOn(command: Command)
}