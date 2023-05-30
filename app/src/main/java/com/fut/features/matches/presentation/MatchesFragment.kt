package com.fut.features.matches.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fut.R
import com.fut.core.utils.*
import com.fut.core.utils.extensions.convertToDateInUsFormat
import com.fut.core.utils.extensions.currentDayInFormatDdMm
import com.fut.core.utils.extensions.getSharedPreferences
import com.fut.core.utils.extensions.push
import com.fut.core.utils.recyclerview.adapters.GenericSectionAdapter
import com.fut.databinding.DayCellBinding
import com.fut.databinding.FragmentMatchesBinding
import com.fut.features.matches.data.models.response.DateResponse
import com.fut.features.matches.data.models.response.FixtureInfo
import com.fut.features.matches.data.models.response.LeagueFixtures
import com.fut.features.matches.data.models.response.LeagueSections
import com.fut.features.matches.domain.FixturesEntity
import com.fut.features.matches.presentation.cell.DayCell
import com.fut.features.matches.presentation.cell.LeagueCell
import com.fut.features.matches.presentation.cell.MatchCell
import com.fut.features.matches.presentation.extension.setupSearchView
import com.fut.utils.extensions.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import io.github.enicolas.genericadapter.AdapterHolderType
import io.github.enicolas.genericadapter.adapter.BaseCell
import io.github.enicolas.genericadapter.adapter.GenericRecyclerAdapter
import io.github.enicolas.genericadapter.adapter.GenericRecylerAdapterDelegate
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

private typealias SectionDelegate = GenericSectionAdapter.GenericSectionRecylerAdapterDelegate

@AndroidEntryPoint
class MatchesFragment : Fragment() {

    private lateinit var param1: String
    private lateinit var param2: String

    companion object {
        const val ARG_PARAM1 = "arg1"
        const val ARG_PARAM2 = "arg2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MatchesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private var _binding: FragmentMatchesBinding? = null
    val binding: FragmentMatchesBinding
        get() = requireNotNull(_binding)

    val viewModel: MatchesFragmentViewModel by viewModels()
    private val datesAdapter = GenericRecyclerAdapter()
    private var allFixtures = mutableListOf<FixtureInfo>()
    var filteredFixtures = mutableListOf<LeagueFixtures>()
    var leagueSections = LeagueSections()
    private var preferredLeagues = mutableSetOf<String>()
    private var preferredCountry = ""
    private var leagueTypes = listOf<String>()
    private var dates = mutableListOf<DateResponse>()

    private val leaguesFixturesAdapter = SectionedRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1).toString()
            param2 = it.getString(ARG_PARAM2).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment()
    }

    private fun setupFragment() {
        fetchData()
        setupSearchView()
        setupBtnViewAllMatches()
    }

    private fun setupBtnViewAllMatches() {
        binding.txtBtnAllGames.setOnClickListener {
            push(AllMatchesFragment.newInstance(leagueSections, ArrayList(preferredLeagues)))
        }
    }

    fun setupLeaguesFixturesRecyclerView(leagues: ArrayList<LeagueFixtures>) {
        leaguesFixturesAdapter.removeAllSections()
        binding.rcvLeagues.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        getPreferredLeagues()

        val onlyPreferredLeagues = leagues.filter { league ->
            preferredLeagues.contains(league.name)
        }

        binding.rcvLeagues.toggleVisibility(onlyPreferredLeagues.isNotEmpty())
        binding.ctlEmptyLeaguesPlaceholder.toggleVisibility(onlyPreferredLeagues.isEmpty())
        if (onlyPreferredLeagues.isEmpty()) return

        onlyPreferredLeagues.forEach { league ->
            val sectionLeagueAdapter = GenericSectionAdapter(
                MatchCell.resId,
                LeagueCell.resId,
                league.games
            )

            sectionLeagueAdapter.delegate = buildSectionLeagueAdapter(league)
            leaguesFixturesAdapter.addSection(sectionLeagueAdapter)
        }

        binding.rcvLeagues.adapter = leaguesFixturesAdapter
    }

    private fun getPreferredLeagues() {
        preferredLeagues = getSharedPreferences()?.getStringSet(
            Constants.USER_PREF_SELECTED_LEAGUES_NAME,
            mutableSetOf()
        ) ?: mutableSetOf()
    }

    private fun getPreferredCountry() {
        preferredCountry = getSharedPreferences()?.getString(
            Constants.SELECTED_COUNTRY_NAME, ""
        ).toString()
    }

    private fun buildSectionLeagueAdapter(league: LeagueFixtures): SectionDelegate =
        object : SectionDelegate {

            override fun numberOfRows(sectionAdapter: GenericSectionAdapter<*>): Int =
                league.games.size

            override fun cellHeader(
                sectionAdapter: GenericSectionAdapter<*>,
                cell: RecyclerView.ViewHolder
            ) {
                (cell as LeagueCell).setup(league)
            }

            override fun cellForPosition(
                sectionAdapter: GenericSectionAdapter<*>,
                cell: RecyclerView.ViewHolder,
                position: Int
            ) {
                val fixture = league.games[position]
                (cell as MatchCell).setup(fixture)
            }

            override fun cellType(sectionAdapter: GenericSectionAdapter<*>, view: View): BaseCell {
                return MatchCell(view)
            }

            override fun cellHeaderType(
                sectionAdapter: GenericSectionAdapter<*>,
                view: View
            ): BaseCell {
                return LeagueCell(view)
            }
        }

    private fun fetchData() {
        getDates()
    }

    private fun getDates() {
        val year = 2023
        val dateFormat = SimpleDateFormat("dd/MM")

        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.DAY_OF_YEAR, 1)

        while (calendar.get(Calendar.YEAR) == year) {
            dates.add(DateResponse(date = dateFormat.format(calendar.time)))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        setupDatesRecyclerView()
    }

    private fun setupDatesRecyclerView() {
        binding.rcvDays.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvDays.adapter = datesAdapter
        datesAdapter.delegate = datesDelegate
        datesAdapter.snapshot?.snapshotList = dates
        setupDefaultValueRecyclerView()
        setupHeaderRecyclerView()
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.scvLeaguesContainer.toggleVisibility(!isLoading)
        binding.pgbLoading.toggleVisibility(isLoading)
    }

    private fun setupHeaderRecyclerView() {
        binding.rcvDays.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val dfs = DateFormatSymbols.getInstance(Locale("pt", "BR"))
                val month = dates[lastVisibleItemPosition].date.split("/").last().toInt()
                val monthName = dfs.months[month - 1].capitalize(Locale("pt", "BR"))
                binding.txtMonth.text = monthName
            }
        })
    }

    private fun setupDefaultValueRecyclerView() {
        val todayDate = dates.firstOrNull { it.date == currentDayInFormatDdMm() }
        val todayDatePosition = dates.indexOf(todayDate)
        binding.rcvDays.scrollToPosition(todayDatePosition)
        binding.rcvDays.post {
            val viewHolder = binding.rcvDays.findViewHolderForAdapterPosition(todayDatePosition)
            viewHolder?.itemView?.performClick()
        }
    }

    private fun getFixtures(date: String) {
        viewModel.getFixtures(Constants.TIMEZONE_SP, date)
            .observe(viewLifecycleOwner) { viewState ->
                when (viewState) {
                    is ViewState.Success -> handleGetFitxturesSuccess(viewState)
                    is ViewState.Error -> handleGetFixturesError()
                    is ViewState.Loading -> {}
                }
            }
    }

    private fun handleGetFixturesError() {
        showSnackWith(R.string.matches_error_searching_games, SnackBarType.Error)
        handleLoading(isLoading = false)
    }

    private fun handleGetFitxturesSuccess(viewState: ViewState<ResponseWrapper.Success<FixturesEntity>>) {
        leagueSections.leagues?.clear()

        allFixtures =
            viewState.data?.result?.response?.toMutableList() ?: arrayListOf()

        leagueTypes =
            ArrayList(allFixtures).map { it.league.name.toString() }.toMutableSet()
                .toList()

        leagueTypes.forEach { leagueName ->
            val leagueGamesList = allFixtures.filter { fixture ->
                fixture.league.name == leagueName
            }

            leagueSections.leagues?.add(
                LeagueFixtures(
                    name = leagueName,
                    games = ArrayList(leagueGamesList)
                )
            )
        }

        setupLeaguesFixturesRecyclerView(leagueSections.leagues ?: arrayListOf())
        handleLoading(isLoading = false)
    }

    private val datesDelegate = object : GenericRecylerAdapterDelegate {

        override fun registerCellAtPosition(
            adapter: GenericRecyclerAdapter,
            position: Int
        ): AdapterHolderType {
            return AdapterHolderType(
                viewBinding = DayCellBinding::class.java,
                clazz = DayCell::class.java,
                0
            )
        }

        override fun cellForPosition(
            adapter: GenericRecyclerAdapter,
            cell: RecyclerView.ViewHolder,
            position: Int
        ) {
            val date = dates[position]
            (cell as DayCell).setup(date) {
                dates.map { it.isSelected = date.date == it.date }
                binding.rcvDays.adapter?.notifyDataSetChanged()
                getFixtures(date.date.convertToDateInUsFormat())
                handleLoading(isLoading = true)
            }
        }

        override fun numberOfRows(adapter: GenericRecyclerAdapter): Int = dates.size
    }
}