package com.fut.core.utils.recyclerview.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fut.R
import com.fut.core.utils.recyclerview.cell.SectionCell
import io.github.enicolas.genericadapter.adapter.BaseCell
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

class GenericSectionAdapter<T> : Section {

    interface GenericSectionRecylerAdapterDelegate {
        fun cellForPosition(
            sectionAdapter: GenericSectionAdapter<*>,
            cell: RecyclerView.ViewHolder,
            position: Int
        )

        fun cellHeader(sectionAdapter: GenericSectionAdapter<*>, cell: RecyclerView.ViewHolder) {}

        fun cellType(sectionAdapter: GenericSectionAdapter<*>, view: View): BaseCell

        fun cellHeaderType(sectionAdapter: GenericSectionAdapter<*>, view: View): BaseCell {
            return SectionCell(view)
        }

        fun didSelectItemAt(sectionAdapter: GenericSectionAdapter<*>, index: Int) {}

        fun numberOfRows(sectionAdapter: GenericSectionAdapter<*>): Int? {
            return null
        }
    }

    var title: String? = null
    var list: MutableList<T>

    var delegate: GenericSectionRecylerAdapterDelegate? = null

    constructor(resId: Int, title: String?, list: MutableList<T>) : super(
        if (title != null) {
            SectionParameters.builder()
                .itemResourceId(resId)
                .headerResourceId(R.layout.match_cell)
                .emptyViewWillBeProvided()
                .build()
        } else {
            SectionParameters.builder()
                .itemResourceId(resId)
                .emptyViewWillBeProvided()
                .build()
        }
    ) {

        this.title = title
        this.list = list

    }

    constructor(resId: Int, headerResId: Int, list: MutableList<T>) : super(
        SectionParameters.builder()
            .itemResourceId(resId)
            .headerResourceId(headerResId)
            .emptyViewWillBeProvided()
            .build()
    ) {
        this.list = list
    }

    constructor(resId: Int, list: MutableList<T>) : super(
        SectionParameters.builder()
            .itemResourceId(resId)
            .emptyViewWillBeProvided()
            .build()
    ) {
        this.list = list
    }

    override fun getContentItemsTotal(): Int {
        return delegate?.numberOfRows(this) ?: list.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseCell)?.prepareForReuse()

        delegate?.cellForPosition(this, holder, position)

        holder.itemView.setOnClickListener {
            val index = holder.adapterPosition
            if (index != RecyclerView.NO_POSITION) {
                delegate?.didSelectItemAt(this, index)
            }
        }
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {
        title?.let { title ->
            val cell = holder as SectionCell
            cell.set(title)
        }

        delegate?.cellHeader(this, holder)
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        delegate?.let { delegate ->
            return delegate.cellType(this, view)
        }

        return BaseCell(view)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        delegate?.let { delegate ->
            return delegate.cellHeaderType(this, view)
        }

        return SectionCell(view)
    }

}