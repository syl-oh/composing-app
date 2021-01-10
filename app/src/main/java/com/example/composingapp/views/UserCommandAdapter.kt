package com.example.composingapp.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.composingapp.R
import com.example.composingapp.utils.interfaces.ui.CommandReceiver
import com.example.composingapp.views.models.UserCommandModel

class UserCommandAdapter(
        val receiver: CommandReceiver,
        private val userCommandButtonData: MutableList<UserCommandModel>
) : RecyclerView.Adapter<UserCommandAdapter.UserCommandButtonHolder>() {

    class UserCommandButtonHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userCommandButton: UserCommandButton = view.findViewById(R.id.user_command_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCommandButtonHolder {
        val userCommandButton: View =
                LayoutInflater.from(parent.context).inflate(R.layout.user_command_button_item, parent, false)
        return UserCommandButtonHolder(userCommandButton)
    }

    override fun onBindViewHolder(holder: UserCommandButtonHolder, position: Int) {
        holder.userCommandButton.imageID = userCommandButtonData[position].imageID
        holder.userCommandButton.command = userCommandButtonData[position].command
        holder.userCommandButton.receiver = receiver
    }

    override fun getItemCount(): Int {
        return userCommandButtonData.size
    }
}