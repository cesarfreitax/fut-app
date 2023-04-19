package com.fut.features.matches.presentation.cell

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.fut.R
import com.fut.core.utils.extensions.load
import com.fut.databinding.MatchCellBinding
import com.fut.features.matches.data.models.response.FixtureInfo
import com.fut.core.utils.extensions.lessThanTenHandler
import com.fut.utils.extensions.makeVisible
import io.github.enicolas.genericadapter.adapter.BaseCell
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MatchCell(private val binding: MatchCellBinding) : BaseCell(binding) {
    constructor(view: View) : this(MatchCellBinding.bind(view))

    companion object {
        const val resId = R.layout.match_cell
    }

    private lateinit var match: FixtureInfo

    @RequiresApi(Build.VERSION_CODES.O)
    fun setup(data: FixtureInfo) {
        match = data
        setHomeTeam()
        setTimer()
        setAwayTeam()
        setScore()
    }

    private fun setScore() {
        match.goals.let { goal ->
            goal.home?.let { goalHome ->
                binding.txtScoreHome.makeVisible()
                binding.txtScoreHome.text = goalHome.toString()
            }
            goal.away?.let { goalAway ->
                binding.txtScoreAway.makeVisible()
                binding.txtScoreAway.text = goalAway.toString()
            }
        }
        match.goals.away?.let {
            binding.txtScoreAway.text = it.toString()
        }
    }

    private fun setAwayTeam() {
        binding.imgAway.load(match.teams.away?.logo, binding.root.context)
        binding.txtAway.text = match.teams.away?.name
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setTimer() {
        val timerText = when (match.fixture.status?.long) {
            "Match Postponed" -> {
                "Remarcada"
            }
            "Match Finished" -> {
                "Finalizada"
            }
            "Not Started" -> {
                val formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
                val dateTime = LocalDateTime.parse(match.fixture.date.toString(), formatter)
                val hour = dateTime.hour
                val minutes = dateTime.minute
                binding.root.context.getString(
                    R.string.matches_game_hour,
                    hour.lessThanTenHandler(),
                    minutes.lessThanTenHandler()
                )
            }
            else -> {
                "Acontecendo agora"
            }
        }

        binding.txtGameHour.text = timerText

    }

    private fun setHomeTeam() {
        binding.imgHome.load(match.teams.home?.logo, binding.root.context)
        binding.txtHome.text = match.teams.home?.name
    }

}