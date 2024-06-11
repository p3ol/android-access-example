package com.example.viewbasedexample.ui.article

import android.os.Build
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewbasedexample.R
import com.example.viewbasedexample.databinding.FragmentBottomSheetBinding
import com.example.viewbasedexample.model.Article
import com.example.viewbasedexample.ui.home.AccessSerializable
import com.example.viewbasedexample.ui.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.poool.access.Access
import com.poool.access.compose.PaywallMode


class ArticleBottomSheetFragment: BottomSheetDialogFragment() {

    private lateinit var article: Article
    private lateinit var accessSerializable: AccessSerializable

    private var _binding: FragmentBottomSheetBinding? = null

    private val binding get() = _binding!!

    private lateinit var articleContainer: ConstraintLayout

    private lateinit var headerImg: ImageView
    private lateinit var headerTitle: TextView
    private lateinit var headerMetadata: TextView

    private lateinit var listParagraph: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            article = arguments?.getSerializable("article", Article::class.java) as Article
            accessSerializable = arguments
                ?.getSerializable("accessSerializable", AccessSerializable::class.java)
                    as AccessSerializable
        } else {
            article = arguments?.getSerializable("article") as Article
            accessSerializable = arguments?.getSerializable("accessSerializable")
                    as AccessSerializable
        }
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        articleContainer = binding.articleContainer

        headerImg = binding.articleImg
        headerTitle = binding.articleHeadline
        headerMetadata = binding.articleMetadata

        listParagraph = binding.listParagraph
        listParagraph.layoutManager = LinearLayoutManager(context)
        listParagraph.adapter = ArticleAdapter(
            article.paragraphs,
            access = if (article.paywallType == PaywallMode.CUSTOM)
                accessSerializable.access else null,
        )

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
        val access = accessSerializable.access

        view.viewTreeObserver.addOnGlobalLayoutListener {
            val dialog = dialog as? BottomSheetDialog
            val bottomSheet =
                dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet!!)

            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        if (article.isPremium && article.paywallType == PaywallMode.BOTTOM_SHEET) {
            access.createBottomSheetPaywall("premium", articleContainer) {
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(
            article: Article,
            accessSerializable: AccessSerializable
        ): ArticleBottomSheetFragment{
            val fragment = ArticleBottomSheetFragment()
            val args = Bundle()
            args.putSerializable("article", article)
            args.putSerializable("accessSerializable", accessSerializable)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
