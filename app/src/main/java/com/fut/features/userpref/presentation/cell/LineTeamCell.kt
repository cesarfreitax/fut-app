package com.fut.features.userpref.presentation.cell

import androidx.core.content.ContextCompat
import com.fut.R
import com.fut.databinding.LineTeamCellBinding
import com.fut.features.search.domain.TeamInfoEntity
import com.fut.core.utils.extensions.load
import io.github.enicolas.genericadapter.adapter.BaseCell

class LineTeamCell(private val binding: LineTeamCellBinding) : BaseCell(binding) {

    private lateinit var teamInfo: TeamInfoEntity

    fun setup(data: TeamInfoEntity, callback: () -> Unit) {
        teamInfo = data
        setTeamImage()
        setTeamName()
        binding.ctlCell.setOnClickListener {
            callback()
        }
        if (teamInfo.isSelected == true) {
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

    private fun setTeamName() {
        binding.txtTeam.text = teamInfo.team.name
    }

    private fun setTeamImage() {
        binding.imgTeam.load(teamInfo.team.logo, binding.root.context)
    }
}