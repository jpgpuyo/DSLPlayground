package org.firezenk.dslplayground

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.firezenk.dslplayground.util.dsl

class MainAdapter : ListAdapter<ListItem, MainAdapter.ViewHolder>(ItemDiffCallback()) {

    private lateinit var clickListener: (ListItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(getItem(position), clickListener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val viewForeground = itemView.findViewById<View>(R.id.viewForeground)
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val subtitle = itemView.findViewById<TextView>(R.id.subtitle)
        private val image = itemView.findViewById<ImageView>(R.id.image)

        fun bind(item: ListItem, clickListener: (ListItem) -> Unit) {
            title.text = item.title
            subtitle.text = item.subtitle
            image.dsl { url = item.image }
            itemView.setOnClickListener { clickListener(item) }
        }
    }

    fun setOnItemClickListener(block: (ListItem) -> Unit) {
        clickListener = block
    }
}

class ItemDiffCallback : DiffUtil.ItemCallback<ListItem>() {

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}