package com.fut.features.userpref.presentation.cell

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fut.R
import com.fut.databinding.LineCellBinding
import com.fut.features.userpref.domain.CountryInfoEntity
import com.fut.core.utils.extensions.load
import io.github.enicolas.genericadapter.adapter.BaseCell

class LineCountryCell(private val binding: LineCellBinding) : BaseCell(binding) {

    private lateinit var countryInfo: CountryInfoEntity

    fun setup(data: CountryInfoEntity, activity: AppCompatActivity, callback: () -> Unit) {
        countryInfo = data
        setupCell(activity)
        binding.ctlCell.setOnClickListener {
            callback()
        }
        if (countryInfo.isSelected == true) {
            binding.ctlCell.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorAccent
                )
            )
        } else {
            binding.ctlCell.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.dark_container
                )
            )
        }
    }

    private fun setupCell(activity: AppCompatActivity) {
        setImage(activity)
        setName()
    }

    private fun setImage(activity: AppCompatActivity) {
        binding.imgCell.load(countryInfo.flag, activity)
    }

    private fun setName() {
        binding.txtCell.text = countryInfo.name
    }
}