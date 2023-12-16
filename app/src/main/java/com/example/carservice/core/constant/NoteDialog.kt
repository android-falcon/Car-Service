package com.example.carservice.core.constant

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

class NoteDialog(private val context: Context,
                 private val onItemCheckedListener: (String) -> Unit
    ) {

    var note: String? = null // Variable to store the entered text

    fun showDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Enter Note")

        // Set up the input
        val input = EditText(context)
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton("OK") { _, _ ->
            val enteredText = input.text.toString()
            onItemCheckedListener(enteredText,)
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }
}