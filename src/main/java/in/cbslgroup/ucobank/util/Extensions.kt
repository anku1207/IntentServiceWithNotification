package `in`.cbslgroup.ucobank.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.widget.Toast
import dmax.dialog.SpotsDialog

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.processBarDialog(backBtnCancel :Boolean ,dialogMessage : String): Dialog {
    return  SpotsDialog.Builder()
        .setContext(this)
        .setMessage(dialogMessage)
        .setCancelable(backBtnCancel)
        .build()
}