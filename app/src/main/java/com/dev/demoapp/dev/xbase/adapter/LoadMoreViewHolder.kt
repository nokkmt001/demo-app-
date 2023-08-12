package com.dev.demoapp.dev.xbase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.demoapp.R

class LoadMoreViewHolder<T>(view: View) : BaseRecyclerViewViewHolder<T>(view) {

    override fun bind(item: T) {}

    companion object {
        fun <M>create(parent: ViewGroup): LoadMoreViewHolder<M> {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_load_more, parent, false)
            return LoadMoreViewHolder(itemView)
        }
    }

}
