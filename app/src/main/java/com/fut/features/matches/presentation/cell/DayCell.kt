package com.fut.features.matches.presentation.cell

import android.graphics.Typeface
import androidx.core.content.ContextCompat
import com.fut.R
import com.fut.core.utils.Constants
import com.fut.core.utils.extensions.currentDayInFormatDdMm
import com.fut.databinding.DayCellBinding
import com.fut.features.matches.data.models.response.DateResponse
import io.github.enicolas.genericadapter.adapter.BaseCell

class DayCell(val binding: DayCellBinding) : BaseCell(binding) {

    private lateinit var dateInfo: DateResponse

    fun setup(response: DateResponse, callback: () -> Unit) {
        dateInfo = response
        setDay()
        setSelection()
        setClick(callback)
    }

    private fun setClick(callback: () -> Unit) {
        binding.cdvBg.setOnClickListener {
            callback()
        }
    }

    private fun setDay() {
        val currentDateSplitted = currentDayInFormatDdMm().split("/")
        val currentDay = currentDateSplitted.first().toInt()
        val currentMonth = currentDateSplitted.last().toInt()
        val dateSplitted = dateInfo.date.split("/")
        val dateDay = dateSplitted.first().toInt()
        val dateMonth = dateSplitted.last().toInt()
        val dayText = if (currentMonth == dateMonth) {
            when (dateDay) {
                currentDay - 1 -> {
                    Constants.YESTERDAY
                }
                currentDay -> {
                    Constants.TODAY
                }
                currentDay + 1 -> {
                    Constants.TOMORROW
                }
                else -> {
                    dateInfo.date
                }
            }
        } else {
            dateInfo.date
        }
        binding.txtDay.text = dayText
    }

    private fun setSelection() {
        if (dateInfo.isSelected == true) {
            binding.cdvBg.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.colorAccent))
            binding.txtDay.typeface = Typeface.DEFAULT_BOLD
            binding.txtDay.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
        } else {
            binding.cdvBg.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.dark_container))
            binding.txtDay.typeface = Typeface.DEFAULT
            binding.txtDay.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
        }
    }
}