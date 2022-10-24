package com.ielts.preparation.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.ielts.preparation.R

class Progress(
    context: Context?,
    @StringRes private val titleRes: Int
) {

    private var view: View? = null
    private var builder: AlertDialog.Builder
    private var dialog: Dialog

    init {
        view = LayoutInflater.from(context).inflate(R.layout.progress, null)
        view?.findViewById<TextView>(R.id.text)?.setText(titleRes)
        builder = AlertDialog.Builder(context)
        builder.setView(view)
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        dialog = builder.create()
        dialog.setCancelable(false)
    }

    fun setProgressMessage(@StringRes titleRes: Int) {
        view?.findViewById<TextView>(R.id.text)?.setText(titleRes)
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

    fun setProgressDialogVisibility(isVisible: Boolean) {

    }
}