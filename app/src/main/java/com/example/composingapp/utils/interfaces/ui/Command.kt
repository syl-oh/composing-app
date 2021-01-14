package com.example.composingapp.utils.interfaces.ui

interface Command {
    /**
     * Performs the Command as instantiated
     */
    fun execute()

    /**
     * Undoes the last execution of the Command
     */
    fun undo()
}