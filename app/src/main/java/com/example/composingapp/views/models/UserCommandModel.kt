package com.example.composingapp.views.models

import com.example.composingapp.utils.interfaces.ui.Command

/**
 * Data class for storing data of user commands in the UI
 *
 * @param imageID Int representing the image resource ID
 * @param command Command to load into the UI component
 */
data class UserCommandModel(
        val imageID: Int,
        val command: Command
) {}