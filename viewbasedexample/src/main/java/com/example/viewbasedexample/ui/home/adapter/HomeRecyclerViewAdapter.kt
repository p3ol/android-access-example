package com.example.viewbasedexample.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.viewbasedexample.R
import com.example.viewbasedexample.databinding.FragmentHomeBinding
import com.example.viewbasedexample.model.Article

class HomeRecyclerViewAdapter(
    private val dataset: List<Article>
) : RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeViewHolder>() {

    private lateinit var context: Context
    private var clickListener: OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(position: Int) {
            // Do nothing
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeRecyclerViewAdapter.HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_home, parent, false)
        context = parent.context

        return HomeViewHolder(
            view,
            clickListener
        )
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewAdapter.HomeViewHolder, position: Int) {
        val bindingAdapterPosition = holder.bindingAdapterPosition
        val item = dataset[position]

        holder.image.setImageResource(item.imageThumbId)
        holder.title.text = item.title
        holder.metadata.text = context.getString(
            R.string.home_article_min_read, item.metadata.date, item.metadata.readTimeMinutes)

    }

    override fun getItemCount(): Int = dataset.size

    fun setOnItemClickListener(listener: OnItemClickListener) {
        clickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class HomeViewHolder(
        itemView: View,
        listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.item_image)
        val title: TextView = itemView.findViewById(R.id.item_title)
        val metadata: TextView = itemView.findViewById(R.id.item_metadata)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition)
            }
        }
    }
}
