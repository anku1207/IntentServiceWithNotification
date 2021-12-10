package `in`.cbslgroup.ucobank.customdialog

import `in`.cbslgroup.ucobank.util.Utility
import `in`.cbslgroup.ucobank.util.showShortToast
import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.ucobank_module.R
import java.util.*


object MyDialog {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun responseDialogCbsl(
        context: Context, headingText: String?, messageText: String?, backBtnCloseDialog: Boolean,
        stringIntegerMap: Map<String?, Int?>, click: (m: String) -> Unit
    ) {
        try {
            val btnName ="Ok"
            val background: Drawable = Utility.getDrawableResources(
                context,
                stringIntegerMap["background"]!!
            )
            val icon: Drawable = Utility.getDrawableResources(context, stringIntegerMap["icon"]!!)
            val btn_background: Drawable = Utility.getDrawableResources(
                context,
                stringIntegerMap["btn_background"]!!
            )
            val btn_color = stringIntegerMap["btn_color"]!!
            val cusdialog = Dialog(context)
            cusdialog.requestWindowFeature(1)
            Objects.requireNonNull(cusdialog.getWindow())?.setBackgroundDrawable(ColorDrawable(0))
            cusdialog.setContentView(R.layout.response_dialog_design)


            cusdialog.setCanceledOnTouchOutside(false)
            cusdialog.setCancelable(backBtnCloseDialog)
            val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.bounce)
            val textHeading: TextView = cusdialog.findViewById(R.id.textHeading)
            val message: TextView = cusdialog.findViewById(R.id.message)
            val buttonOk: Button = cusdialog.findViewById(R.id.buttonOk)
            val iconHeadingLayout: LinearLayout = cusdialog.findViewById(R.id.iconHeadingLayout)
            val imageView: ImageView = cusdialog.findViewById(R.id.imageView)
            iconHeadingLayout.background = background

            //btn set values
            buttonOk.text=btnName
            buttonOk.background=btn_background

            //imageView.setAnimation(R.raw.success);
            imageView.setImageDrawable(icon)
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(cusdialog.getWindow()!!.getAttributes())
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            if (headingText == null || headingText.isEmpty()) {
                textHeading.visibility = View.GONE
            } else {
                textHeading.visibility = View.VISIBLE
                textHeading.text = headingText
                textHeading.animation = animation
            }
            if (messageText == null || messageText.isEmpty()) {
                message.visibility = View.GONE
            } else {
                message.visibility = View.VISIBLE
                message.text = messageText
            }
            buttonOk.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    Utility.dismissDialog(context,cusdialog)
                    click("btnClick")
                }
            })
            if (!(context as Activity).isFinishing && !cusdialog.isShowing()) cusdialog.show()
            cusdialog.getWindow()?.setAttributes(lp)
        } catch (e: Exception) {
            e.message?.let { context.showShortToast(it) }
        }
    }

}