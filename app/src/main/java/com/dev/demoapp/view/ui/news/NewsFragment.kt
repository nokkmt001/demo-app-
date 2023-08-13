package com.dev.demoapp.view.ui.news

import com.dev.demoapp.R
import com.dev.demoapp.databinding.FragmentNewsBinding
import com.dev.demoapp.dev.xbase.BaseMvvmFragment
import com.dev.demoapp.model.News
import com.dev.demoapp.view.ui.adapter.NewsAdapter

class NewsFragment : BaseMvvmFragment<FragmentNewsBinding>(R.layout.fragment_news) {

    private var adapter = NewsAdapter()

    override fun startFlow() {
        super.startFlow()

        binding.rclMain.adapter = adapter
        adapter.clear()
        adapter.addAll(getListData())
    }

    private fun getListData(): List<News> {
        var list = ArrayList<News>()
        var item = News(
            1,
            "test",
            "I've had a worst day and it just kept getting worse. I don't know what to do anymore and it feels so hard to want"
        )
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        return list
    }

}
