package com.github.lzaytseva.it_cron_test_task.util

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.github.lzaytseva.it_cron_test_task.R
import com.google.android.material.snackbar.Snackbar

object FeedbackUtils {
    fun showSnackbar(root: View, text: String) {
        val snackbar =
            Snackbar.make(root, text, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(
            ContextCompat.getColor(
                root.context,
                R.color.snackbar_bg
            )
        )
        snackbar.setTextColor(
            ContextCompat.getColor(
                root.context,
                R.color.snackbar_text_color
            )
        )
        val textView =
            snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snackbar.show()
    }
}
