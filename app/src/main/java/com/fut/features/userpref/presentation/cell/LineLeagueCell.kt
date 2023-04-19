package com.fut.features.userpref.presentation.cell

import androidx.core.content.ContextCompat
import com.fut.R
import com.fut.core.utils.extensions.load
import com.fut.databinding.LineLeagueCellBinding
import com.fut.features.userpref.domain.LeagueInfoEntity
import io.github.enicolas.genericadapter.adapter.BaseCell

class LineLeagueCell(private val binding: LineLeagueCellBinding) : BaseCell(binding) {

    private lateinit var leagueInfo: LeagueInfoEntity

    fun setup(data: LeagueInfoEntity, callback: () -> Unit) {
        setupCell(data, callback)

    }

    private fun setupCell(data: LeagueInfoEntity, callback: () -> Unit) {
        leagueInfo = data
        setCallback(callback)
        setBgColor()
        setImage()
        setName()
    }

    private fun setBgColor() {
        if (leagueInfo.isSelected == true) {
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

    private fun setCallback(callback: () -> Unit) {
        binding.ctlCell.setOnClickListener {
            callback()
        }
    }

    private fun setImage() {
        binding.imgLeague.load(leagueInfo.league.logo, binding.root.context)
    }

    private fun setName() {
        binding.txtLeague.text = leagueInfo.league.name
    }
}