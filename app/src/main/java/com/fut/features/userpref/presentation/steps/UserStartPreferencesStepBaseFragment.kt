package com.fut.features.userpref.presentation.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fut.core.utils.Constants
import com.fut.core.utils.extensions.getSharedPreferences
import com.fut.core.utils.extensions.push
import com.fut.databinding.FragmentUserStartPreferencesStepBaseBinding
import com.fut.features.main.MainActivity
import com.fut.features.userpref.presentation.UserStartPreferencesActivity
import com.fut.features.userpref.presentation.UserStartPreferencesViewModel
import com.fut.features.userpref.presentation.extension.setupFetchData
import com.fut.features.userpref.presentation.extension.setupSearchView
import dagger.hilt.android.AndroidEntryPoint
import io.github.enicolas.genericadapter.adapter.GenericRecyclerAdapter

@AndroidEntryPoint
open class UserStartPreferencesStepBaseFragment : Fragment() {

    companion object {
        fun newInstance() = UserStartPreferencesStepBaseFragment()
    }

    private var _binding: FragmentUserStartPreferencesStepBaseBinding? = null
    val binding: FragmentUserStartPreferencesStepBaseBinding
        get() = requireNotNull(_binding)

    lateinit var viewModel: UserStartPreferencesViewModel
    val adapter = GenericRecyclerAdapter()
    var response: MutableList<Any> = mutableListOf()
    var data: MutableList<Any> = mutableListOf()
    open lateinit var step: UserStartPreferencesStepEnum
    val currentStep: UserStartPreferencesStepEnum
        get() = step

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[UserStartPreferencesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserStartPreferencesStepBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment()
    }

    private fun setupFragment() {
        setupFetchData()
        setupSearchView()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as UserStartPreferencesActivity).apply {
            setLayout(step = currentStep)
            setupButtons()
        }

    }

    private fun UserStartPreferencesActivity.setupButtons() {
        binding.btnBack.setOnClickListener {
            supportFragmentManager.popBackStack()
        }
        binding.btnNextOrFinish.setOnClickListener {
            setNavigation(currentStep)
        }
    }

    private fun setNavigation(step: UserStartPreferencesStepEnum) {
        when (step) {
            UserStartPreferencesStepEnum.CHOOSE_COUNTRY -> {
                push(ChooseTeamFragment())
            }
            UserStartPreferencesStepEnum.CHOOSE_TEAM -> {
                push(ChooseLeagueFragment())
            }
            UserStartPreferencesStepEnum.CHOOSE_LEAGUE -> {
                savePreferences()
                push(activity = MainActivity(), clearStack = true)
            }
        }
    }

    private fun savePreferences() {
        val db = getSharedPreferences()?.edit()
        db?.apply {
            putString(Constants.SELECTED_COUNTRY_NAME, viewModel.selectedCountry)
            putString(Constants.SELECTED_TEAM_NAME, viewModel.selectedTeamName)
            putString(Constants.SELECTED_TEAM_ID, viewModel.selectedTeamId)
            putStringSet(
                Constants.USER_PREF_SELECTED_LEAGUES_ID,
                viewModel.selectedLeaguesId.map { it.toString() }.toMutableSet()
            )
            putStringSet(
                Constants.USER_PREF_SELECTED_LEAGUES_NAME,
                viewModel.selectedLeaguesName.toMutableSet()
            )
        }?.apply()
    }

}