package com.dev.demoapp.view.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dev.demoapp.R
import com.dev.demoapp.databinding.RowCategoriesBinding
import com.dev.demoapp.databinding.RowNewsBinding
import com.dev.demoapp.dev.xbase.adapter.BaseMainAdapter
import com.dev.demoapp.dev.xbase.adapter.BaseRecyclerViewViewHolder
import com.dev.demoapp.model.Categories
import com.dev.demoapp.model.News

class NewsAdapter : BaseMainAdapter<News>() {

    override fun onCreateHolder(view: ViewGroup): BaseRecyclerViewViewHolder<News> {
        return ItemRowNews(
            DataBindingUtil.inflate(
                LayoutInflater.from(view.context),
                R.layout.row_news,
                view,
                false
            )
        )
    }

    class ItemRowNews(val view: RowNewsBinding) :
        BaseRecyclerViewViewHolder<News>(view.root) {
        override fun bind(item: News) {
            view.data = item
        }
    }

}
