package com.dev.demoapp.view.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dev.demoapp.R
import com.dev.demoapp.databinding.RowCategoriesBinding
import com.dev.demoapp.dev.xbase.adapter.BaseMainAdapter
import com.dev.demoapp.dev.xbase.adapter.BaseRecyclerViewViewHolder
import com.dev.demoapp.model.Categories

class CategoriesAdapter : BaseMainAdapter<Categories>() {

    override fun onCreateHolder(view: ViewGroup): BaseRecyclerViewViewHolder<Categories> {
        return ItemRowCategories(
            DataBindingUtil.inflate<RowCategoriesBinding?>(
                LayoutInflater.from(view.context),
                R.layout.row_categories,
                view,
                false
            ).apply {

            }
        ).apply {
          itemView.setOnClickListener {
              val item = mList[bindingAdapterPosition]
              if (item.isSelect == true) {
                  mList[bindingAdapterPosition].isSelect = false
                  itemView.setBackgroundResource(R.drawable.table_content_cell_radius_5)
              } else {
                  itemView.setBackgroundResource(R.drawable.button_register_normal)
                  mList[bindingAdapterPosition].isSelect = true
              }

            }

        }
    }

    class ItemRowCategories(val view: RowCategoriesBinding) :
        BaseRecyclerViewViewHolder<Categories>(view.root) {
        override fun bind(item: Categories) {
            view.data = item
        }
    }

}
