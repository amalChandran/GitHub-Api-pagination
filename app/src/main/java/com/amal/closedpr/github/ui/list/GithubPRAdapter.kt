package com.amal.closedpr.github.ui.list

import android.view.ViewGroup
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import com.amal.closedpr.github.repository.model.PRModelItem

class GithubPRAdapter :
    PagingDataAdapter<PRModelItem, PRViewHolder>(PR_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PRViewHolder.create(parent)

    override fun onBindViewHolder(holder: PRViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    fun withRefreshHeaderAndAppendFooter(
        header: LoadStateAdapter<*>,
        footer: LoadStateAdapter<*>
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            header.loadState = loadStates.refresh
            footer.loadState = loadStates.append
        }
        return ConcatAdapter(header, this, footer)
    }

    companion object {
        val PR_COMPARATOR = object : DiffUtil.ItemCallback<PRModelItem>() {
            override fun areContentsTheSame(oldItem: PRModelItem, newItem: PRModelItem): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: PRModelItem, newItem: PRModelItem): Boolean =
                oldItem.title == newItem.title
        }
    }
}