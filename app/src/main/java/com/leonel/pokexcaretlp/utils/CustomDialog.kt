package com.leonel.pokexcaretlp.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.leonel.pokexcaretlp.R

class CustomDialog (private val context: Context) {

    private val dialog = Dialog(context)
    init{

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

    }
    fun one(description: String,
            titleOfPositiveButton: String? = null,
            titleOfNegativeButton: String? = null,
            positiveButtonFunction: (() -> Unit)? = null,
            negativeButtonFunction: (() -> Unit)? = null
    ) : Dialog {

        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_custom)
        val dialogDescription = dialog.findViewById(R.id.content) as TextView
        val dialogPositiveButton = dialog.findViewById(R.id.bt_positive) as Button
        val dialogNegativeButton = dialog.findViewById(R.id.bt_negative) as Button

        dialogDescription.text = description
        titleOfPositiveButton?.let { dialogPositiveButton.text = it } ?: dialogPositiveButton.text
        titleOfNegativeButton?.let { dialogNegativeButton.text = it } ?: dialogNegativeButton.text

        if(titleOfNegativeButton.isNullOrEmpty())
            dialogNegativeButton.visibility =View.GONE

        dialogPositiveButton.setOnClickListener {
            positiveButtonFunction?.invoke()
            dialog.dismiss()
        }
        dialogNegativeButton.setOnClickListener {
            negativeButtonFunction?.invoke()
            dialog.dismiss()
        }


        return dialog

    }

}