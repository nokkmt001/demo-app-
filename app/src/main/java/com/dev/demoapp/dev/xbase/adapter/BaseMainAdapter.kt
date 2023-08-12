package com.dev.demoapp.dev.xbase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.dev.demoapp.dev.common.AppResource

abstract class BaseMainAdapter<T> :
    RecyclerView.Adapter<BaseRecyclerViewViewHolder<T>>(), DefaultLifecycleObserver {
    protected open var mList = mutableListOf<T>()
    private val VIEW_TYPE_ITEM = 1
    private val VIEW_TYPE_LOAD_MORE = 2
    private var isLoadMoreOrRefresh = false

    private var context: Context? = null

    init {
        context = AppResource.application
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewViewHolder<T> {
        return if (viewType == VIEW_TYPE_LOAD_MORE) {
            LoadMoreViewHolder.create(parent)
        } else {
            onCreateHolder(parent)
        }
    }

    protected fun bindLayout(layoutID: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context)
            .inflate(layoutID, parent, false)
    }

    protected abstract fun onCreateHolder(view: ViewGroup): BaseRecyclerViewViewHolder<T>

    open fun onViewHolderCreated(holder: BaseRecyclerViewViewHolder<T>) {}

    open fun setupActions(itemView: View, item: T, position: Int) {}

    override fun onBindViewHolder(holder: BaseRecyclerViewViewHolder<T>, position: Int) {
        if (holder !is LoadMoreViewHolder) {
            holder.bind(mList[position])
            onViewHolderCreated(holder)
            setupActions(holder.itemView, mList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    open fun addAll(list: List<T>?) {
        if (isLoadMoreOrRefresh) removeLoadingFooter()
        isLoadMoreOrRefresh = false
        if (!list.isNullOrEmpty()) {
            mList.addAll(list)
            notifyDataSetChanged()
        }
    }

    open fun clear() {
        isLoadMoreOrRefresh = false
        mList.clear()
        notifyDataSetChanged()
    }

    open fun addLoadingFooter(e: T?) {
        isLoadMoreOrRefresh = true
        mList.add(e!!)
        notifyItemInserted(mList.size - 1)
    }

    open fun getItem(position: Int): T? {
        return mList[position]
    }

    open fun removeLoadingFooter() {
        isLoadMoreOrRefresh = false
        val position = mList.size - 1
        if (position == -1) return
        val o: T = getItem(position)!!
        if (o != null) {
            mList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    open fun add(e: T?) {
        if (mList == null) mList.clear()
        isLoadMoreOrRefresh = false
        mList.add(e!!)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mList.size - 1 &&
            isLoadMoreOrRefresh
        ) VIEW_TYPE_LOAD_MORE else VIEW_TYPE_ITEM
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyed() {
        this.clear()
    }

}
