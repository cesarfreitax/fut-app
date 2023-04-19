package com.fut.features.userpref.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fut.R
import com.fut.core.utils.extensions.push
import com.fut.databinding.ActivityUserStartPreferencesBinding
import com.fut.features.userpref.presentation.steps.ChooseCountryFragment
import com.fut.features.userpref.presentation.steps.ChooseTeamFragment
import com.fut.features.userpref.presentation.steps.UserStartPreferencesStepEnum
import com.fut.utils.extensions.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserStartPreferencesActivity : AppCompatActivity() {

    private var _binding: ActivityUserStartPreferencesBinding? = null
    val binding: ActivityUserStartPreferencesBinding
        get() = requireNotNull(_binding)
    private lateinit var currentStep: UserStartPreferencesStepEnum
    private lateinit var viewModel: UserStartPreferencesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserStartPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[UserStartPreferencesViewModel::class.java]
        if (savedInstanceState == null) {
            push(ChooseCountryFragment())
        }
    }

    fun setLayout(step: UserStartPreferencesStepEnum) {
        currentStep = step
        setHeader(step)
        setProgress(step)
        setButtons(step)
        checkButtonState(false)
    }

    private fun setHeader(step: UserStartPreferencesStepEnum) {
        val titleAndSubtitle = when (step) {
            UserStartPreferencesStepEnum.CHOOSE_COUNTRY -> getString(R.string.userstartpreferences_choose_country_title) to null
            UserStartPreferencesStepEnum.CHOOSE_TEAM -> getString(R.string.userstartpreferences_choose_team_title) to null
            UserStartPreferencesStepEnum.CHOOSE_LEAGUE -> getString(R.string.userstartpreferences_choose_league_title) to getString(
                R.string.userstartpreferences_choose_league_subtitle
            )
        }
        val title = titleAndSubtitle.first
        val subtitle = titleAndSubtitle.second.orEmpty()

        binding.txtTitle.text = title
        binding.txtSubtitle.text = subtitle
    }

    private fun setProgress(step: UserStartPreferencesStepEnum) {
        when (step) {
            UserStartPreferencesStepEnum.CHOOSE_COUNTRY -> binding.pgbProgress.progress = 1
            UserStartPreferencesStepEnum.CHOOSE_TEAM -> binding.pgbProgress.progress = 2
            UserStartPreferencesStepEnum.CHOOSE_LEAGUE -> binding.pgbProgress.progress = 3
        }
    }

    private fun setButtons(step: UserStartPreferencesStepEnum) {
        binding.btnBack.toggleVisibility(step != UserStartPreferencesStepEnum.CHOOSE_COUNTRY)
        binding.btnNextOrFinish.text = getString(
            when (step) {
                UserStartPreferencesStepEnum.CHOOSE_COUNTRY -> R.string.userstartpreferences_btn_next
                UserStartPreferencesStepEnum.CHOOSE_TEAM -> R.string.userstartpreferences_btn_next
                UserStartPreferencesStepEnum.CHOOSE_LEAGUE -> R.string.userstartpreferences_btn_finish
            }
        )
    }

    fun checkButtonState(enable: Boolean) {
        binding.btnNextOrFinish.apply {
            alpha = if (enable) 1f else .3f
            isClickable = enable
            isFocusable = enable
            isEnabled = enable
        }
    }

    override fun onResume() {
        super.onResume()
        binding.btnBack.setOnClickListener {
            supportFragmentManager.popBackStack()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (currentStep != UserStartPreferencesStepEnum.CHOOSE_TEAM) {
            super.onBackPressed()
        }
    }
}