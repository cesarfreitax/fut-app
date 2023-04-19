package com.fut.features.highlights.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fut.core.utils.Constants
import com.fut.core.utils.SnackBarType
import com.fut.core.utils.ViewState
import com.fut.core.utils.extensions.exceptionHandler
import com.fut.core.utils.extensions.getSharedPreferences
import com.fut.core.utils.extensions.wrapContent
import com.fut.core.utils.showSnackWith
import com.fut.databinding.FragmentHighlightsBinding
import com.fut.databinding.NewsCellBinding
import com.fut.features.highlights.domain.NewsEntity
import com.fut.features.highlights.domain.NewsInfoEntity
import com.fut.features.highlights.presentation.cell.NewsCell
import com.fut.utils.extensions.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import io.github.enicolas.genericadapter.AdapterHolderType
import io.github.enicolas.genericadapter.adapter.GenericRecyclerAdapter
import io.github.enicolas.genericadapter.adapter.GenericRecylerAdapterDelegate

@AndroidEntryPoint
class HighlightsFragment : Fragment() {

    private var _binding: FragmentHighlightsBinding? = null
    private val binding: FragmentHighlightsBinding
        get() = requireNotNull(_binding)
    private val viewModel: HighlightsViewModelFragment by viewModels()
    private val newsAdapter = GenericRecyclerAdapter()
    private val leaguesNewsAdapter = GenericRecyclerAdapter()
    private var news = mutableListOf<NewsInfoEntity>()
    private var leaguesNews = mutableListOf<NewsInfoEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHighlightsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment()
    }

    private fun setupFragment() {
        fetchData()
    }

    private fun fetchData() {
        getTeamNews()
    }


    private fun getTeamNews() {
        viewModel.getEverything(getSelectedTeam(), Constants.PUBLISHED_AT)
            .observe(viewLifecycleOwner) { viewState ->
                when (viewState) {
                    is ViewState.Success -> onGetTeamNewsSuccess(viewState)
                    is ViewState.Loading -> {}
                    is ViewState.Error -> onGetTeamNewsError(viewState)
                }
            }
    }

    private fun getLeagueNews() {
        val db = getSharedPreferences()
        val firstPreferedLeague =
            db?.getStringSet(Constants.USER_PREF_SELECTED_LEAGUES_NAME, mutableSetOf())
        if (firstPreferedLeague?.isNotEmpty() == true) {
            viewModel.getEverything(firstPreferedLeague.first(), Constants.PUBLISHED_AT)
                .observe(viewLifecycleOwner) { viewState ->
                    when (viewState) {
                        is ViewState.Success -> onGetLeagueNewsSuccess(viewState)
                        is ViewState.Loading -> {}
                        is ViewState.Error -> onGetLeagueNewsError(viewState)
                    }
                }
        }

    }


    private fun onGetLeagueNewsSuccess(viewState: ViewState<NewsEntity?>) {
        leaguesNews = viewState.data?.articles?.toMutableList() ?: mutableListOf()
        setupLeaguesRecyclerView()
        stopLoading()
    }

    private fun onGetLeagueNewsError(viewState: ViewState<NewsEntity?>) {
        showSnackWith(viewState.messageResId ?: 0, SnackBarType.Error)
        stopLoading()
    }

    private fun setupLeaguesRecyclerView() {
        binding.rcvLeagueNews.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rcvLeagueNews.isNestedScrollingEnabled = false
        binding.rcvLeagueNews.adapter = leaguesNewsAdapter
        leaguesNewsAdapter.delegate = leaguesNewsDelegate
        leaguesNewsAdapter.snapshot?.snapshotList = leaguesNews
    }


    private fun onGetTeamNewsError(viewState: ViewState<NewsEntity?>) {
        showSnackWith(viewState.messageResId ?: 0, SnackBarType.Error)
        getLeagueNews()
    }

    private fun onGetTeamNewsSuccess(viewState: ViewState<NewsEntity?>) {
        exceptionHandler {
            setTeamNewsFilter(viewState)
        }
        setupRecyclerViewTeamNews()
        getLeagueNews()
    }

    private fun stopLoading() {
        binding.pgbLoading.toggleVisibility(false)
        binding.txtTitle.toggleVisibility(true)
        binding.txtRcvTeamNews.toggleVisibility(true)
        binding.rcvTeamNews.toggleVisibility(true)
        binding.txtRcvLeagueNews.toggleVisibility(true)
        binding.rcvLeagueNews.toggleVisibility(true)
    }

    private fun setTeamNewsFilter(viewState: ViewState<NewsEntity?>) {
        val selectedTeamName = getSharedPreferences()?.getString(Constants.SELECTED_TEAM_NAME, "")

        news = viewState.data?.articles?.filter { article ->
            article.title?.lowercase()
                ?.contains(
                    selectedTeamName?.lowercase() ?: "futebol"
                ) == true && Constants.NEWS_FILTER_WORDS.any { word ->
                article.content?.lowercase()?.contains(word) == true
            }
        }?.toMutableList() ?: mutableListOf()
    }

    private fun getSelectedTeam(): String =
        getSharedPreferences()?.getString(Constants.SELECTED_TEAM_NAME, "").orEmpty()


    private fun setupRecyclerViewTeamNews() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rcvTeamNews.isNestedScrollingEnabled = false
        binding.rcvTeamNews.layoutManager = layoutManager
        binding.rcvTeamNews.adapter = newsAdapter
        newsAdapter.delegate = newsDelegate
        newsAdapter.snapshot?.snapshotList = news
    }

    private val leaguesNewsDelegate = object : GenericRecylerAdapterDelegate {

        override fun registerCellAtPosition(
            adapter: GenericRecyclerAdapter,
            position: Int
        ): AdapterHolderType {
            return AdapterHolderType(
                viewBinding = NewsCellBinding::class.java,
                clazz = NewsCell::class.java,
                0
            )
        }

        override fun cellForPosition(
            adapter: GenericRecyclerAdapter,
            cell: RecyclerView.ViewHolder,
            position: Int
        ) {
            val new = leaguesNews[position]
            (cell as NewsCell).setup(new)
            binding.rcvLeagueNews.wrapContent()
        }

        override fun numberOfRows(adapter: GenericRecyclerAdapter): Int = leaguesNews.size
    }

    private val newsDelegate = object : GenericRecylerAdapterDelegate {

        override fun registerCellAtPosition(
            adapter: GenericRecyclerAdapter,
            position: Int
        ): AdapterHolderType {
            return AdapterHolderType(
                viewBinding = NewsCellBinding::class.java,
                clazz = NewsCell::class.java,
                0
            )
        }

        override fun cellForPosition(
            adapter: GenericRecyclerAdapter,
            cell: RecyclerView.ViewHolder,
            position: Int
        ) {
            val new = news[position]
            (cell as NewsCell).setup(new)
            binding.rcvTeamNews.wrapContent()
        }

        override fun numberOfRows(adapter: GenericRecyclerAdapter): Int = news.size
    }

    private fun getLeagues(): List<String>? {
        val db = getSharedPreferences()
        return db?.getStringSet(Constants.USER_PREF_SELECTED_LEAGUES_NAME, mutableSetOf())?.toList()
            ?: listOf()
    }
}