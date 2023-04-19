package com.fut.core.utils.recyclerview.cell

import android.view.View
import android.widget.TextView
import com.fut.R
import io.github.enicolas.genericadapter.adapter.BaseCell

class SectionCell(view: View) : BaseCell(view) {

    private var titleTextView: TextView = view.findViewById(R.id.txt_header)

    fun set(title: String) {
        titleTextView.text = title
    }

}