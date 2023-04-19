package com.fut.features.highlights.presentation.cell

import com.fut.core.utils.extensions.load
import com.fut.databinding.NewsCellBinding
import com.fut.features.highlights.domain.NewsInfoEntity
import io.github.enicolas.genericadapter.adapter.BaseCell
import java.text.SimpleDateFormat
import java.util.*

class NewsCell(val binding: NewsCellBinding) : BaseCell(binding) {

    private lateinit var new: NewsInfoEntity

    fun setup(data: NewsInfoEntity) {
        this.new = data
        setImage()
        setTitle()
        setSubtitle()
        setDate()
    }


    private fun setTitle() {
        binding.txtNewsTitle.text = new.title
    }

    private fun setSubtitle() {
        val splittedContent = new.content?.split("[")
        val subtitle = splittedContent?.first()
        binding.txtNewsSubtitle.text = subtitle
    }

    private fun setImage() {
        binding.imgNews.load(new.urlToImage, binding.root.context)
    }

    private fun setDate() {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val date = inputFormat.parse(new.publishedAt.toString())
        val formattedDate = outputFormat.format(date as Date)
        binding.txtDate.text = formattedDate
    }
}
