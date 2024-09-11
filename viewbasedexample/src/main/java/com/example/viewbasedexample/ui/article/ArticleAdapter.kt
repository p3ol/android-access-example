package com.example.viewbasedexample.ui.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.viewbasedexample.R
import com.example.viewbasedexample.model.Paragraph
import com.example.viewbasedexample.model.ParagraphType
import com.poool.access.Access
import com.poool.access.compose.PaywallMode

class ArticleAdapter internal constructor(
    private val paragraphs: List<Paragraph>,
    private val access: Access? = null,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val PREMIUM_LIMIT = 3

    override fun getItemViewType(position: Int): Int {
        if (access != null && position == PREMIUM_LIMIT) {
            return 2
        }

        return when (paragraphs[position].type) {
            ParagraphType.Header -> 0
            ParagraphType.Text -> 1
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_bottom_sheet_header_item, parent, false)
                HeaderViewHolder(view)
            }
            2 -> {
                val view = access
                    ?.returnPaywallView("premium", parent.context) ?: View(parent.context)

                PaywallViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_bottom_sheet_text_item, parent, false)
                TextViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.textView.text = paragraphs[position].text
            }
            is TextViewHolder -> {
                holder.textView.text = paragraphs[position].text
            }
            is PaywallViewHolder -> {
                // Do nothing
            }
        }
    }

    override fun getItemCount(): Int = if (access != null) (PREMIUM_LIMIT + 1) else paragraphs.size

    inner class PaywallViewHolder internal constructor(view: View) :
        RecyclerView.ViewHolder(view) {

        val paywallView: View = view
    }

    inner class HeaderViewHolder internal constructor(view: View) :
        RecyclerView.ViewHolder(view) {

        val textView: TextView = (view as TextView)
    }

    inner class TextViewHolder internal constructor(view: View) :
        RecyclerView.ViewHolder(view) {

        val textView: TextView = (view as TextView)
    }
}
