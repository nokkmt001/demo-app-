package com.dev.demoapp.dev.xbase.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * @param T is a data class
 */
abstract class BaseRecyclerViewViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: T)

}
