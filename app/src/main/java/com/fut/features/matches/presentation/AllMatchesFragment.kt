package com.fut.features.matches.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fut.core.utils.recyclerview.adapters.GenericSectionAdapter
import com.fut.databinding.FragmentAllMatchesBinding
import com.fut.features.main.MainActivity
import com.fut.features.matches.data.models.response.LeagueFixtures
import com.fut.features.matches.data.models.response.LeagueSections
import com.fut.features.matches.presentation.cell.LeagueCell
import com.fut.features.matches.presentation.cell.MatchCell
import io.github.enicolas.genericadapter.adapter.BaseCell
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter

private const val LEAGUES = "leagues"
private const val PREFERRED_LEAGUES = "preferredLeagues"
private typealias AllMatchesSectionDelegate = GenericSectionAdapter.GenericSectionRecylerAdapterDelegate

class AllMatchesFragment : Fragment() {

    private var _binding: FragmentAllMatchesBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    private var leagueSections: LeagueSections? = null
    private var preferredLeagueNames: ArrayList<String>? = null
    private val leagueFixturesAdapter = SectionedRecyclerViewAdapter()

    companion object {
        @JvmStatic
        fun newInstance(leagues: LeagueSections, preferredLeagueNames: ArrayList<String>) =
            AllMatchesFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(LEAGUES, leagues)
                    putStringArrayList(PREFERRED_LEAGUES, preferredLeagueNames)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            leagueSections = it.getSerializable(LEAGUES) as LeagueSections
            preferredLeagueNames = it.getStringArrayList(PREFERRED_LEAGUES)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllMatchesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment()
    }

    private fun setupFragment() {
        setupAllLeaguesRecyclerView()
    }

    private fun setupAllLeaguesRecyclerView() {
        leagueFixturesAdapter.removeAllSections()
        binding.rcvAllLeagues.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        sortByPreferredLeagues()
        setSections()
        binding.rcvAllLeagues.adapter = leagueFixturesAdapter
    }

    private fun sortByPreferredLeagues() {
        val preferredLeagues = mutableListOf<LeagueFixtures>()
        val otherLeagues = mutableListOf<LeagueFixtures>()

        leagueSections?.leagues?.forEach { league ->
            if (preferredLeagueNames?.contains(league.name) == true) {
                preferredLeagues.add(league)
            } else {
                otherLeagues.add(league)
            }
        }

        leagueSections?.leagues?.clear()
        leagueSections?.leagues?.addAll(preferredLeagues + otherLeagues)
    }

    private fun setSections() {
        leagueSections?.leagues?.forEach { league ->
            val sectionLeagueAdapter = GenericSectionAdapter(
                MatchCell.resId,
                LeagueCell.resId,
                league.games
            )

            sectionLeagueAdapter.delegate = buildSectionLeagueAdapter(league)
            leagueFixturesAdapter.addSection(sectionLeagueAdapter)
        }
    }

    private fun buildSectionLeagueAdapter(league: LeagueFixtures): AllMatchesSectionDelegate =
        object : AllMatchesSectionDelegate {

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
                val match = league.games[position]
                (cell as MatchCell).setup(match)
            }

            override fun cellHeaderType(
                sectionAdapter: GenericSectionAdapter<*>,
                view: View
            ): BaseCell {
                return LeagueCell(view)
            }

            override fun cellType(sectionAdapter: GenericSectionAdapter<*>, view: View): BaseCell {
                return MatchCell(view)
            }
        }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).bnvMenuVisibility = false
    }
}