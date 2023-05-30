package com.fut.features.matches.presentation.cell

import android.view.View
import androidx.core.content.ContextCompat
import com.fut.R
import com.fut.core.utils.extensions.lessThanTenHandler
import com.fut.core.utils.extensions.load
import com.fut.databinding.MatchCellBinding
import com.fut.features.matches.data.models.response.FixtureInfo
import com.fut.utils.extensions.toggleVisibility
import io.github.enicolas.genericadapter.adapter.BaseCell
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MatchCell(private val binding: MatchCellBinding) : BaseCell(binding) {
    constructor(view: View) : this(MatchCellBinding.bind(view))

    companion object {
        const val resId = R.layout.match_cell
    }

    enum class Status(val value: String) {

        // SCHEDULED
        TIME_TO_BE_DEFINED("Time To Be Defined"), NOT_STARTED(value = "Not Started"),

        // IN PLAY
        FIRST_HALF(value = "First Half"), KICK_OFF(value = "Kick Off"), HALF_TIME(value = "Halftime"), SECOND_HALF(
            value = "Second Half"
        ),
        SECOND_HALF_STARTED(value = "2nd Half Started"), EXTRA_TIME(value = "Extra Time"), BREAK_TIME(
            value = "Break Time"
        ),
        PENALTY_IN_PROGRESS(value = "Penalty In Progress"), MATCH_SUSPENDED(value = "Match Suspended"), MATCH_INTERRUPTED(
            value = "Match Interrupted"
        ),
        IN_PROGRESS(value = "In Progress"),

        // FINISHED
        MATCH_FINISHED(value = "Match Finished"), MATCH_FINISHED_AFTER_EXTRA_TIME(value = "Match Finished After Extra Time"), MATCH_FINISHED_AFTER_PENALTY(
            value = "Match Finished After Penalty"
        ),

        // POSTPONED
        MATCH_POSTPONED(value = "Match Postponed"),

        // CANCELLED
        MATCH_CANCELLED(value = "Match Cancelled"),

        // ABANDONED
        MATCH_ABANDONED(value = "Match Abandoned"),

        // NOT PLAYED
        TECHNICAL_LOSS(value = "Technical Loss"), WALKOVER(value = "WalkOver"),

    }

    private lateinit var match: FixtureInfo

    fun setup(data: FixtureInfo) {
        match = data
        setHomeTeam()
        setTimer()
        setAwayTeam()
        setScore()
    }

    private fun setScore() {
        match.goals.home?.let { homeGoal ->
            binding.txtScoreHome.text = homeGoal.toString()
        }

        match.goals.away?.let { awayGoal ->
            binding.txtScoreAway.text = awayGoal.toString()
        }

        binding.txtScoreHome.toggleVisibility(binding.txtScoreHome.text.isNotEmpty())
        binding.txtScoreAway.toggleVisibility(binding.txtScoreAway.text.isNotEmpty())
    }

    private fun setAwayTeam() {
        binding.imgAway.load(match.teams.away?.logo, binding.root.context)
        binding.txtAway.text = match.teams.away?.name
    }

    private fun setTimer() {
        match.fixture.status?.long?.let { status ->
            when (status) {
                Status.TIME_TO_BE_DEFINED.value -> {
                    binding.txtGameStatus.text =
                        binding.root.context.getString(R.string.matches_status_to_be_confirmed)
                    binding.txtGameStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.neutral1
                        )
                    )
                }
                Status.NOT_STARTED.value -> {
                    // SCHEDULED
                }
                Status.FIRST_HALF.value,
                Status.KICK_OFF.value,
                Status.HALF_TIME.value,
                Status.SECOND_HALF.value,
                Status.SECOND_HALF_STARTED.value,
                Status.EXTRA_TIME.value,
                Status.BREAK_TIME.value,
                Status.PENALTY_IN_PROGRESS.value,
                Status.MATCH_SUSPENDED.value,
                Status.MATCH_INTERRUPTED.value,
                Status.IN_PROGRESS.value -> {
                    binding.txtGameStatus.text =
                        binding.root.context.getString(R.string.matches_status_inprogress)
                    binding.txtGameStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context, R.color.green_success
                        )
                    )
                }
                Status.MATCH_FINISHED.value,
                Status.MATCH_FINISHED_AFTER_EXTRA_TIME.value,
                Status.MATCH_FINISHED_AFTER_PENALTY.value -> {
                    binding.txtGameStatus.text =
                        binding.root.context.getString(R.string.matches_status_finished)
                    binding.txtGameStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context, R.color.neutral1
                        )
                    )
                }
                Status.MATCH_POSTPONED.value -> {
                    binding.txtGameStatus.text =
                        binding.root.context.getString(R.string.matches_status_postponed)
                    binding.txtGameStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context, R.color.yellow_warning
                        )
                    )
                }
                Status.MATCH_CANCELLED.value -> {
                    binding.txtGameStatus.text =
                        binding.root.context.getString(R.string.matches_status_cancelled)
                    binding.txtGameStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context, R.color.garnet_flu_500
                        )
                    )
                }
                Status.MATCH_ABANDONED.value -> {
                    binding.txtGameStatus.text =
                        binding.root.context.getString(R.string.matches_status_abandoned)
                    binding.txtGameStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context, R.color.garnet_flu_500
                        )
                    )
                }
                Status.TECHNICAL_LOSS.value, Status.WALKOVER.value -> {
                    binding.txtGameStatus.text =
                        binding.root.context.getString(R.string.matches_status_not_played)
                    binding.txtGameStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context, R.color.garnet_flu_500
                        )
                    )
                }
            }
        }


        val formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
        val dateTime = LocalDateTime.parse(match.fixture.date.toString(), formatter)
        val hour = dateTime.hour
        val minutes = dateTime.minute
        binding.txtGameHour.text = binding.root.context.getString(
            R.string.matches_game_hour, hour.lessThanTenHandler(), minutes.lessThanTenHandler()
        )

        binding.txtGameStatus.toggleVisibility(binding.txtGameStatus.text.isNotEmpty())
        binding.txtGameHour.toggleVisibility(binding.txtGameHour.text.isNotEmpty())
    }

    private fun setHomeTeam() {
        binding.imgHome.load(match.teams.home?.logo, binding.root.context)
        binding.txtHome.text = match.teams.home?.name
    }

}