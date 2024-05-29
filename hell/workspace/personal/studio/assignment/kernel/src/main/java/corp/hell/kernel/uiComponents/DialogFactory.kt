package corp.hell.kernel.uiComponents

import android.app.Activity
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import corp.hell.kernel.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Copyright © 2023 56 AI Technologies. All rights reserved.
 *
 * @author: Harsh Gupta
 * @Date: March 02, 2023
 */
object DialogFactory {

    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog


    fun showCrashCustomAlertDialog(
        activity: Activity?, positiveClickAction: (() -> Unit)?,
        negativeClickAction: (() -> Unit)?,
    ) {
        activity ?: return
        if (this::dialog.isInitialized && dialog.isShowing) {
            return
        }
        builder = AlertDialog.Builder(ContextThemeWrapper(activity, R.style.CustomAlertDialog))
        val customView =
            LayoutInflater.from(activity)
                .inflate(R.layout.crash_pop_up, null, false)
        builder.setView(customView)
        dialog = builder.create()

        val btnPositive = customView.findViewById<Button>(R.id.btnRestart)
        val btnNegative = customView.findViewById<LinearLayout>(R.id.llReport)
        val tvReport = customView.findViewById<TextView>(R.id.tvReport)


        btnNegative.setOnClickListener {
            GlobalScope.launch {
                btnNegative.isEnabled = false
//                tvReport.text = ctx.getString(R.string.reporting)
                delay(2000)
//                tvReport.text = ctx.getString(R.string.reported)
                delay(1000)
                dialog.dismiss()
                negativeClickAction?.invoke()
            }
        }
        btnPositive.setOnClickListener {
            dialog.dismiss()
            positiveClickAction?.invoke()
        }
        dialog.setCancelable(false)
        dialog.show()
    }
}