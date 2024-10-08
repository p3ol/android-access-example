package com.example.viewbasedexample.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setMargins
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.viewbasedexample.R
import com.example.viewbasedexample.databinding.FragmentHomeBinding
import com.example.viewbasedexample.model.Article
import com.example.viewbasedexample.model.ArticlesFeed
import com.example.viewbasedexample.ui.article.ArticleBottomSheetFragment
import com.example.viewbasedexample.ui.home.adapter.HomeRecyclerViewAdapter
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var _binding: FragmentHomeBinding

    private lateinit var loader: LinearLayout

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var homeScrollView: NestedScrollView

    private lateinit var mainLinearLayout: LinearLayout
    private lateinit var topSection: LinearLayout
    private lateinit var topSectionImage: ImageView
    private lateinit var topSectionHeadline: TextView
    private lateinit var topSectionMetadata: TextView

    private lateinit var homeRecyclerView: RecyclerView

    private lateinit var popularLinearLayout: LinearLayout

    private lateinit var adapter: HomeRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = fragmentBinding.root

        _binding = fragmentBinding

        loader = LayoutInflater
            .from(requireContext())
            .inflate(R.layout.main_loader, null) as LinearLayout

        adapter = HomeRecyclerViewAdapter(emptyList())

        swipeRefreshLayout = fragmentBinding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshArticles()
            swipeRefreshLayout.isRefreshing = false
        }
        homeScrollView = fragmentBinding.homeScrollView

        mainLinearLayout = fragmentBinding.mainLinearLayout
        topSection = fragmentBinding.topSectionLinearLayout
        topSectionImage = fragmentBinding.topSectionImg
        topSectionHeadline = fragmentBinding.topSectionHeadline
        topSectionMetadata = fragmentBinding.topSectionMetadata

        homeRecyclerView = fragmentBinding.homeRecyclerView
        homeRecyclerView.adapter = adapter
        homeRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        popularLinearLayout = fragmentBinding.popularHorizontalScrollLinearLayout

        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect { state ->
                when (state) {
                    is HomeUiState.HasArticles -> {
                        if (
                            homeScrollView.getChildAt(0) != mainLinearLayout
                        ) {
                            homeScrollView.removeView(loader)
                            homeScrollView.addView(mainLinearLayout)
                            homeScrollView.requestLayout()
                        }

                        topSection.setOnClickListener {
                            openArticle(state.articlesFeed.highlightedArticle)
                        }

                        adapter = HomeRecyclerViewAdapter(state.articlesFeed.recommendedArticles)
                        homeRecyclerView.adapter = adapter
                        adapter.setOnItemClickListener(object : HomeRecyclerViewAdapter
                            .OnItemClickListener {
                                override fun onItemClick(position: Int) {
                                    openArticle(state.articlesFeed.recommendedArticles[position])
                                }
                            }
                        )

                        hydrateArticles(state.articlesFeed)
                    }
                    is HomeUiState.NoArticles -> {
                        homeScrollView.removeView(mainLinearLayout)
                        homeScrollView.addView(loader)
                        homeScrollView.requestLayout()
                    }
                }
            }
        }

        return fragmentBinding.root
    }

    private fun openArticle(article: Article) {
        val bottomSheetFragment = ArticleBottomSheetFragment
            .newInstance(article, viewModel.accessSerializable)
        bottomSheetFragment.show(
            requireActivity().supportFragmentManager,
            bottomSheetFragment.tag
        )
    }

    private fun hydrateArticles(articlesFeed: ArticlesFeed) {
        val highlightedArticle = articlesFeed.highlightedArticle
        topSectionImage.setImageResource(highlightedArticle.imageId)
        if (highlightedArticle.isPremium) {
            topSectionHeadline.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.baseline_lock_24, 0, 0, 0
            )
        }
        topSectionHeadline.text = highlightedArticle.title
        topSectionMetadata.text = getString(
            R.string.home_article_min_read,
            highlightedArticle.metadata.date,
            highlightedArticle.metadata.readTimeMinutes
        )

        val popularArticles = articlesFeed.popularArticles
        popularLinearLayout.removeAllViews()
        popularArticles.forEach { article ->
            popularLinearLayout.addView(
                popularCardView(article)
            )
        }
    }

    private fun popularCardView(article: Article): MaterialCardView {
        val title = article.title
        val metadata = article.metadata
        val imageId = article.imageThumbId

        val displayMetrics = resources.displayMetrics

        val cardViewWith = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            280f,
            displayMetrics
        )

        val cardView = MaterialCardView(requireContext())
        val cardViewLayoutParams = LinearLayout.LayoutParams(
            cardViewWith.toInt(),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        cardViewLayoutParams.setMargins(0, 8, 8, 0)
        cardView.layoutParams = cardViewLayoutParams
        cardView.cardElevation = 0f
        cardView.preventCornerOverlap = true

        cardView.setOnClickListener { openArticle(article) }

        val linearLayout = LinearLayout(requireContext())
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayout.orientation = LinearLayout.VERTICAL

        cardView.addView(linearLayout)

        val imageHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            100f,
            displayMetrics
        )
        val imageView = ImageView(requireContext())
        imageView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            imageHeight.toInt()
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.contentDescription = resources.getString(R.string.image_of_article)
        imageView.setImageResource(imageId)

        linearLayout.addView(imageView)

        val infoLinearLayout = LinearLayout(requireContext())
        val infoLinearLayoutLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        infoLinearLayoutLayoutParams.setMargins(16)
        infoLinearLayout.layoutParams = infoLinearLayoutLayoutParams
        infoLinearLayout.orientation = LinearLayout.VERTICAL

        linearLayout.addView(infoLinearLayout)

        val titleView = TextView(requireContext())
        titleView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        titleView.setTextAppearance(
            com.google.android.material.R.style.TextAppearance_Material3_TitleMedium)
        titleView.text = title
        if (article.isPremium) {
            titleView.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.baseline_lock_24, 0, 0, 0
            )
        }

        infoLinearLayout.addView(titleView)

        val metadataView = TextView(requireContext())
        metadataView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        metadataView.setPadding(0, 8, 0, 0)
        metadataView.setTextAppearance(
            com.google.android.material.R.style.TextAppearance_Material3_BodyMedium)
        metadataView.text = getString(
            R.string.home_article_min_read,
            metadata.date,
            metadata.readTimeMinutes
        )

        infoLinearLayout.addView(metadataView)

        return cardView
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
