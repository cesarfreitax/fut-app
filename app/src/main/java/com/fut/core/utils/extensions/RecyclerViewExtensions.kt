package com.fut.core.utils.extensions

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.wrapContent() {
    this.post {
        this.measure(
            View.MeasureSpec.UNSPECIFIED,
            View.MeasureSpec.UNSPECIFIED
        )
        val lp = this.layoutParams
        lp.height = this.measuredHeight
        this.layoutParams = lp
    }
}