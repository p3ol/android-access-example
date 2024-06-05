package com.example.viewbasedexample.ui.article

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewbasedexample.R
import com.example.viewbasedexample.databinding.FragmentBottomSheetBinding
import com.example.viewbasedexample.model.Article
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class ArticleBottomSheetFragment(private val article: Article) : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null

    private val binding get() = _binding!!

    private lateinit var headerImg: ImageView
    private lateinit var headerTitle: TextView
    private lateinit var headerMetadata: TextView

    private lateinit var listParagraph: RecyclerView

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        headerImg = binding.articleImg
        headerTitle = binding.articleHeadline
        headerMetadata = binding.articleMetadata

        listParagraph = binding.listParagraph
        listParagraph.layoutManager = LinearLayoutManager(context)
        listParagraph.adapter = ArticleAdapter(article.paragraphs)

        headerImg.setImageResource(article.imageId)
        headerTitle.text = article.title
        headerMetadata.text = getString(
            R.string.home_article_min_read,
            article.metadata.date,
            article.metadata.readTimeMinutes
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.viewTreeObserver.addOnGlobalLayoutListener {
            val dialog = dialog as? BottomSheetDialog
            val bottomSheet =
                dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet!!)

            // Expand the BottomSheetDialogFragment
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    companion object {
        fun newInstance(article: Article): ArticleBottomSheetFragment =
            ArticleBottomSheetFragment(article)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
