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
import androidx.core.view.setPadding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.viewbasedexample.R
import com.example.viewbasedexample.databinding.FragmentHomeBinding
import com.example.viewbasedexample.model.Article
import com.example.viewbasedexample.model.ArticlesFeed
import com.example.viewbasedexample.model.Metadata
import com.example.viewbasedexample.ui.home.adapter.HomeRecyclerViewAdapter
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

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
        _binding = fragmentBinding

        adapter = HomeRecyclerViewAdapter(emptyList())

        swipeRefreshLayout = fragmentBinding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshArticles()
            swipeRefreshLayout.isRefreshing = false
        }

        topSectionImage = fragmentBinding.topSectionImg
        topSectionHeadline = fragmentBinding.topSectionHeadline
        topSectionMetadata = fragmentBinding.topSectionMetadata

        homeRecyclerView = fragmentBinding.homeRecyclerView
        homeRecyclerView.adapter = adapter
        homeRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        popularLinearLayout = fragmentBinding.popularHorizontalScrollLinearLayout

        adapter.setOnItemClickListener(object : HomeRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                println("Item clicked at position $position")
            }
        })

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is HomeUiState.HasArticles -> {
                        adapter = HomeRecyclerViewAdapter(state.articlesFeed.recommendedArticles)
                        homeRecyclerView.adapter = adapter
                        hydrateArticles(state.articlesFeed)
                    }
                    is HomeUiState.NoArticles -> {
                    }
                }
            }
        }

        return fragmentBinding.root
    }

    private fun hydrateArticles(articlesFeed: ArticlesFeed) {
        val highlightedArticle = articlesFeed.highlightedArticle
        topSectionImage.setImageResource(highlightedArticle.imageId)
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
                popularCardView(
                    article.title,
                    article.metadata,
                    article.imageThumbId
                )
            )
        }
    }

    private fun popularCardView(
        title: String,
        metadata: Metadata,
        imageId: Int
    ): MaterialCardView {
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
