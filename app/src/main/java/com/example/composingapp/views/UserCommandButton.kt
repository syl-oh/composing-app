package com.example.composingapp.views

import android.content.Context
import android.util.AttributeSet
import com.example.composingapp.utils.interfaces.ui.Command
import com.example.composingapp.utils.interfaces.ui.CommandReceiver
import com.example.composingapp.views.commands.DefaultCommand

class UserCommandButton : androidx.appcompat.widget.AppCompatImageButton {
    var imageID = 0
        set(value) {
            field = value
            setImageResource(field)
        }
    var command: Command = DefaultCommand(context)
        set(value) {
            field = value
            setOnClickListener { receiver.actOn(command) }
        }
    lateinit var receiver: CommandReceiver

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


}