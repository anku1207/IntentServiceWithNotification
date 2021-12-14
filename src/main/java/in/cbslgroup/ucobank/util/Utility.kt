package `in`.cbslgroup.ucobank.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import com.example.ucobank_module.R
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


object Utility {
    fun validatePattern(value: String?, key: String?): String? {
        return try {
            val valid = JSONObject(key)
            if (value == null) {
                return valid.getString("msg")
            }
            val ptrn: Pattern = Pattern.compile(valid.getString("pattern"))
            val matcher: Matcher = ptrn.matcher(value)
            var errorMsg: String? = null
            if (!matcher.matches()) {
                errorMsg = valid.getString("msg")
            }
            errorMsg
        } catch (e: Exception) {
            Log.w("error", e)
            Constants.SOMETHINGWRONG
        }
    }

    fun getTimeStampToGetTime(timestamp: Long): String? {
        val numberOfHours = (timestamp % 86400 / 3600).toInt()
        val numberOfMinutes = (timestamp % 86400 % 3600 / 60).toInt()
        val numberOfSeconds = (timestamp % 86400 % 3600 % 60).toInt()
        return stringFormat2Digits(numberOfHours).toString() + ":" + stringFormat2Digits(
            numberOfMinutes
        ) + ":" + stringFormat2Digits(numberOfSeconds)
    }

    fun stringFormat2Digits(value: Int): String? {
        return String.format("%02d", value)
    }

    fun getRandomNumberString(): String {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        val rnd = Random()
        val number: Int = rnd.nextInt(999999)

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number)
    }

    fun dismissDialog(context: Context, dialog: Dialog?) {
        try {
            if (!(context as Activity).isFinishing && dialog != null && dialog.isShowing()) {
                dialog.dismiss()
            }
        } catch (e: java.lang.Exception) {
            // ExceptionsNotification.ExceptionHandling(context ,  Utility.getStackTrace(e), "0");
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getDrawableResources(context: Context, id: Int): Drawable {
        val myDrawable: Drawable
        myDrawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.resources.getDrawable(id, context.theme)
        } else {
            context.resources.getDrawable(id)
        }
        return myDrawable
    }

    fun getResponseWishStyleForTxnDialog(
        context: Context?,
        txnType: Boolean
    ): Map<String?, Int?> {
        var stringIntegerMap: MutableMap<String?, Int?> = HashMap()
        if (!txnType) {
            stringIntegerMap = HashMap()
            stringIntegerMap["background"] = R.drawable.round_border_bg_redcolor
            stringIntegerMap["icon"] = R.drawable.fail
            stringIntegerMap["btn_background"] = R.drawable.edittext_round_border_redcolor
            stringIntegerMap["btn_color"] = R.color.actions_bg_orange
        } else {
            stringIntegerMap = HashMap()
            stringIntegerMap["background"] = R.drawable.round_border_bg_appbarcolor
            stringIntegerMap["icon"] = R.drawable.success
            stringIntegerMap["btn_background"] = R.drawable.button_round_border
            stringIntegerMap["btn_color"] = R.color.appbar
        }
        return stringIntegerMap
    }

    fun getColorWrapper(context: Context, id: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(id)
        } else {
            context.resources.getColor(id)
        }
    }

    fun StringToDateWithLenient(dtValue: String?, format: String?): Date? {
        var dt: Date? = Date()
        try {
            val simpleDateFormat = SimpleDateFormat(format)
            simpleDateFormat.isLenient = false
            dt = simpleDateFormat.parse(dtValue)
        } catch (e: ParseException) {
            e.printStackTrace()
            dt = null
        }
        return dt
    }

    fun convertDate2String(dtValue: Date?, format: String?): String? {
        var date: String? = null
        try {
            val dateFormat = SimpleDateFormat(format)
            date = dateFormat.format(dtValue)
        } catch (e: java.lang.Exception) {
        }
        return date
    }

}