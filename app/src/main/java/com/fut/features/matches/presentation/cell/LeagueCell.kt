package com.fut.features.matches.presentation.cell

import android.view.View
import com.fut.R
import com.fut.databinding.LeagueCellBinding
import com.fut.features.matches.data.models.response.LeagueFixtures
import io.github.enicolas.genericadapter.adapter.BaseCell

class LeagueCell(private val binding: LeagueCellBinding) : BaseCell(binding) {
    constructor(view: View) : this(LeagueCellBinding.bind(view))

    companion object {
        const val resId = R.layout.league_cell
    }

    private lateinit var leagueInfo: LeagueFixtures

    fun setup(league: LeagueFixtures) {
        leagueInfo = league
        setTitle()
    }

    private fun setTitle() {
        binding.txtHeader.text = leagueInfo.name
    }

//    private fun setupLeaguesRecyclerView() {
//        val layoutManager =
//            LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
//        binding.rcvFixtures.layoutManager = layoutManager
//        binding.rcvFixtures.isNestedScrollingEnabled = false
//        binding.rcvFixtures.adapter = fixturesAdapter
//        fixturesAdapter.delegate = fixturesDelegate
//        fixturesAdapter.snapshot?.snapshotList = fixtures
//    }
//
//    private val fixturesDelegate = object : GenericRecylerAdapterDelegate {
//
//        override fun registerCellAtPosition(
//            adapter: GenericRecyclerAdapter,
//            position: Int
//        ): AdapterHolderType {
//            return AdapterHolderType(
//                viewBinding = MatchCellBinding::class.java,
//                clazz = MatchCell::class.java,
//                reuseIdentifier = 0
//            )
//        }
//
//        override fun cellForPosition(
//            adapter: GenericRecyclerAdapter,
//            cell: RecyclerView.ViewHolder,
//            position: Int
//        ) {
//            val fixture = fixtures[position]
//            (cell as MatchCell).setup(
//                fixture
//            )
//        }
//
//
//        override fun numberOfRows(adapter: GenericRecyclerAdapter): Int = fixtures.size
//
//        override fun didSelectItemAtIndex(adapter: GenericRecyclerAdapter, index: Int) {
//            super.didSelectItemAtIndex(adapter, index)
//            val id = fixtures[index]
//            binding.rcvFixtures.showSnackWith("Cliquei no jogo com id $id", SnackBarType.Success)
//        }
//
//    }

}