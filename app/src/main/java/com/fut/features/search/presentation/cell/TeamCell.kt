package com.fut.features.search.presentation.cell

import com.fut.R
import com.fut.core.utils.Constants
import com.fut.databinding.CellTeamBinding
import com.fut.features.search.data.models.response.TeamInfo
import com.fut.features.search.domain.TeamInfoEntity
import com.fut.core.utils.extensions.load
import io.github.enicolas.genericadapter.adapter.BaseCell

class TeamCell(private val binding: CellTeamBinding) : BaseCell(binding) {

    private lateinit var teamInfo: TeamInfoEntity

    fun setup(data: TeamInfoEntity) {
        this.teamInfo = data
        setupCell()
    }

    private fun setupCell() {
        setTeamImage()
    }

    private fun setTeamImage() {
        val noImage = (teamInfo.team.logo == Constants.IMAGE_NOT_AVAILABLE_URL) || (teamInfo.team.logo == Constants.IMAGE_SOON_URL)
        if (noImage) {
            binding.imgTeam.load(teamInfo.team.logo, binding.root.context)
        } else {
            binding.imgTeam.setImageResource(R.drawable.unkown_team_placeholder)
        }

        binding.txtTeam.text = teamInfo.team.code
    }
}