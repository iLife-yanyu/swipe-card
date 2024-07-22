package com.yanyu.libs.swipecard

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class CommonViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun dispatch(adapter: CommonAdapter<T>, data: T, position: Int) {

    }
}