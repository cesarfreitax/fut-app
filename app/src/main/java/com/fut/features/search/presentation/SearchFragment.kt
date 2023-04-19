package com.fut.features.search.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fut.core.utils.Constants
import com.fut.core.utils.SnackBarType
import com.fut.core.utils.ViewState
import com.fut.core.utils.showSnackWith
import com.fut.databinding.CellTeamBinding
import com.fut.databinding.FragmentSearchBinding
import com.fut.features.search.data.models.response.TeamInfo
import com.fut.features.search.presentation.cell.TeamCell
import com.fut.features.matches.presentation.MatchesFragment
import com.fut.features.search.domain.TeamInfoEntity
import dagger.hilt.android.AndroidEntryPoint
import io.github.enicolas.genericadapter.AdapterHolderType
import io.github.enicolas.genericadapter.adapter.GenericRecyclerAdapter
import io.github.enicolas.genericadapter.adapter.GenericRecylerAdapterDelegate

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var param1: String
    private lateinit var param2: String

    companion object {
        val ARG_PARAM1 = "arg1"
        val ARG_PARAM2 = "arg2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MatchesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = requireNotNull(_binding)

    private val viewModel: SearchFragmentViewModel by viewModels()
    private val teamsAdapter = GenericRecyclerAdapter()
    private var teams = arrayListOf<TeamInfoEntity>()

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
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment()
    }

    private fun setupFragment() {
        fetchData()
    }

    private fun setupTeamsRecyclerView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rcvTeams.layoutManager = layoutManager
        binding.rcvTeams.adapter = teamsAdapter
        teamsAdapter.delegate = rcvTeamsDelegate
        teamsAdapter.snapshot?.snapshotList = teams
    }

    private var rcvTeamsDelegate = object : GenericRecylerAdapterDelegate {

        override fun registerCellAtPosition(
            adapter: GenericRecyclerAdapter,
            position: Int
        ): AdapterHolderType {
            return AdapterHolderType(
                viewBinding = CellTeamBinding::class.java,
                clazz = TeamCell::class.java,
                reuseIdentifier = 0
            )
        }

        override fun cellForPosition(
            adapter: GenericRecyclerAdapter,
            cell: RecyclerView.ViewHolder,
            position: Int
        ) {
            (cell as TeamCell).let {
                val team = teams[position]
                it.setup(team)
            }
        }

        override fun numberOfRows(adapter: GenericRecyclerAdapter): Int = teams.size

        override fun didSelectItemAtIndex(adapter: GenericRecyclerAdapter, index: Int) {
            super.didSelectItemAtIndex(adapter, index)
        }
    }

    private fun fetchData() {
        viewModel.getTeamsByCountry(Constants.COUNTRY_BRAZIL).observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewState.Success -> {
                    teams = viewState.data?.response ?: arrayListOf()
                    setupTeamsRecyclerView()
                }
                is ViewState.Loading -> {}
                is ViewState.Error -> showSnackWith(viewState.messageResId ?: 0, SnackBarType.Error)
            }
        }
    }
}