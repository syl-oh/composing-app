package com.example.composingapp.utils.interfaces.ui

interface CommandReceiver {
    /**
     * Executes an incoming Command, if applicable
     *
     * @param command Command to execute
     */
    fun actOn(command: Command)
}