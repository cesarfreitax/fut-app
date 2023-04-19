package com.fut.features.userpref.presentation.extension

import androidx.recyclerview.widget.RecyclerView
import com.fut.databinding.LineCellBinding
import com.fut.databinding.LineLeagueCellBinding
import com.fut.databinding.LineTeamCellBinding
import com.fut.features.search.domain.TeamInfoEntity
import com.fut.features.userpref.domain.CountryInfoEntity
import com.fut.features.userpref.domain.LeagueInfoEntity
import com.fut.features.userpref.presentation.UserStartPreferencesActivity
import com.fut.features.userpref.presentation.cell.LineCountryCell
import com.fut.features.userpref.presentation.cell.LineLeagueCell
import com.fut.features.userpref.presentation.cell.LineTeamCell
import com.fut.features.userpref.presentation.steps.UserStartPreferencesStepBaseFragment
import com.fut.features.userpref.presentation.steps.UserStartPreferencesStepEnum
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import io.github.enicolas.genericadapter.AdapterHolderType
import io.github.enicolas.genericadapter.adapter.GenericRecyclerAdapter
import io.github.enicolas.genericadapter.adapter.GenericRecylerAdapterDelegate

private lateinit var delegate: GenericRecylerAdapterDelegate

fun UserStartPreferencesStepBaseFragment.setupRecyclerView() {
    setupDelegate()
    setRecyclerView()
}

private fun UserStartPreferencesStepBaseFragment.setRecyclerView() {
    binding.rcv.layoutManager =
        FlexboxLayoutManager(requireActivity(), FlexDirection.ROW)
    binding.rcv.adapter = adapter
    adapter.delegate = delegate
    adapter.snapshot?.snapshotList = data
    setupDataInAlphabeticalOrder()
}

fun UserStartPreferencesStepBaseFragment.setupDataInAlphabeticalOrder() {
    when (currentStep) {
        UserStartPreferencesStepEnum.CHOOSE_COUNTRY -> {
            (data as ArrayList<CountryInfoEntity>).sortBy { it.name }
        }
        UserStartPreferencesStepEnum.CHOOSE_TEAM -> {
            (data as ArrayList<TeamInfoEntity>).sortBy { it.team.name }
        }
        UserStartPreferencesStepEnum.CHOOSE_LEAGUE -> {
            (data as ArrayList<LeagueInfoEntity>).sortBy { it.league.name }
        }
    }
}

private fun UserStartPreferencesStepBaseFragment.setupDelegate() {
    delegate = object : GenericRecylerAdapterDelegate {

        override fun registerCellAtPosition(
            adapter: GenericRecyclerAdapter,
            position: Int
        ): AdapterHolderType {
            when (currentStep) {
                UserStartPreferencesStepEnum.CHOOSE_COUNTRY -> {
                    return AdapterHolderType(
                        viewBinding = LineCellBinding::class.java,
                        clazz = LineCountryCell::class.java,
                        reuseIdentifier = 0
                    )
                }
                UserStartPreferencesStepEnum.CHOOSE_TEAM -> {
                    return AdapterHolderType(
                        viewBinding = LineTeamCellBinding::class.java,
                        clazz = LineTeamCell::class.java,
                        reuseIdentifier = 0
                    )
                }
                UserStartPreferencesStepEnum.CHOOSE_LEAGUE -> {
                    return AdapterHolderType(
                        viewBinding = LineLeagueCellBinding::class.java,
                        clazz = LineLeagueCell::class.java,
                        reuseIdentifier = 0
                    )
                }
            }

        }

        override fun cellForPosition(
            adapter: GenericRecyclerAdapter,
            cell: RecyclerView.ViewHolder,
            position: Int
        ) {
            var item = data[position]
            when (currentStep) {
                UserStartPreferencesStepEnum.CHOOSE_COUNTRY -> {
                    item = item as CountryInfoEntity
                    val activity = (requireActivity() as UserStartPreferencesActivity)
                    (cell as LineCountryCell).setup(data = item, activity = activity) {
                        didSelectCellHandler(item)
                        val countryName = (item as CountryInfoEntity).name
                        viewModel.selectedCountry = countryName
                    }
                }
                UserStartPreferencesStepEnum.CHOOSE_TEAM -> {
                    item = item as TeamInfoEntity
                    (cell as LineTeamCell).setup(item) {
                        didSelectCellHandler(item)
                        val data = (item as TeamInfoEntity)
                        val teamName = data.team.name
                        val teamId = data.team.id.toString()
                        viewModel.selectedTeamName = teamName
                        viewModel.selectedTeamId = teamId
                    }
                }
                UserStartPreferencesStepEnum.CHOOSE_LEAGUE -> {
                    item = item as LeagueInfoEntity
                    (cell as LineLeagueCell).setup(item) {
                        didSelectCellHandler(item)
                    }
                }
            }

        }

        override fun numberOfRows(adapter: GenericRecyclerAdapter): Int = data.size
    }
}

private fun UserStartPreferencesStepBaseFragment.didSelectCellHandler(cell: Any) {
    checkButtonState(true)
    when (currentStep) {
        UserStartPreferencesStepEnum.CHOOSE_COUNTRY -> {
            (data as MutableList<CountryInfoEntity>).forEach {
                it.isSelected = (cell as CountryInfoEntity).name == it.name
            }
        }
        UserStartPreferencesStepEnum.CHOOSE_TEAM -> {
            (data as MutableList<TeamInfoEntity>).forEach {
                it.isSelected = (cell as TeamInfoEntity).team.id == it.team.id
            }
        }
        UserStartPreferencesStepEnum.CHOOSE_LEAGUE -> {
            val leagueInfo = (cell as LeagueInfoEntity)
            val leagueName = leagueInfo.league.name
            val leagueId = leagueInfo.league.id
            if (contains(leagueId)) {
                viewModel.selectedLeaguesId.remove(leagueId)
                viewModel.selectedLeaguesName.remove(leagueName)
            } else {
                viewModel.selectedLeaguesName.add(leagueName)
                viewModel.selectedLeaguesId.add(leagueId)
            }
            setSelection(data)
            setSelection(response)
        }
    }
    binding.rcv.adapter?.notifyDataSetChanged()
}

fun UserStartPreferencesStepBaseFragment.setSelection(list: MutableList<Any>) {
    (list as MutableList<LeagueInfoEntity>).map {
        it.isSelected = viewModel.selectedLeaguesId.contains(it.league.id)
    }
}

fun UserStartPreferencesStepBaseFragment.checkButtonState(enable: Boolean) {
    (requireActivity() as UserStartPreferencesActivity).checkButtonState(enable)
}

fun UserStartPreferencesStepBaseFragment.contains(leagueId: Int): Boolean =
    viewModel.selectedLeaguesId.contains(leagueId)