package com.amal.closedpr.github.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amal.closedpr.R
import com.amal.closedpr.databinding.RowPrBinding
import com.amal.closedpr.github.repository.model.PRModelItem
import com.bumptech.glide.Glide

class PRViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {
    private val binding = RowPrBinding.bind(itemView)
    private val title: TextView = binding.txtTitle
    private val createdAt: TextView = binding.txtCreatedAt
    private val closedAt: TextView = binding.txtClosedAt
    private val userName: TextView = binding.txtUserName
    private val userImage: ImageView = binding.imgUser


    fun bind(closedPR: PRModelItem?) {
        this.title.text = closedPR?.title ?: "Title missing"
        this.createdAt.text = closedPR?.createdAt ?: "--"
        this.closedAt.text = closedPR?.closedAt ?: "--"
        this.userName.text = closedPR?.user?.name ?: "--"
        Glide.with(userImage.context)
            .load(closedPR?.user?.avatar)
            .into(userImage)
    }

    companion object {
        fun create(parent: ViewGroup): PRViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_pr, parent, false)
            return PRViewHolder(view)
        }
    }
}