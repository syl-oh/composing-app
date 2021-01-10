package com.example.composingapp.views.models

import com.example.composingapp.utils.interfaces.ui.Command

data class UserCommandModel(
        val imageID: Int,
        val command: Command
) {

}