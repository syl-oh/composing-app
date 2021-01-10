package com.example.composingapp.views.commands

import android.content.Context
import android.widget.Toast
import com.example.composingapp.utils.interfaces.ui.Command

class DefaultCommand(val context: Context): Command {
    override fun execute() {
        Toast.makeText(context, "No execute command loaded.", Toast.LENGTH_SHORT).show()
    }

    override fun undo() {
        Toast.makeText(context, "No undo command loaded.", Toast.LENGTH_SHORT).show()
    }

}