package com.yanyu.libs.swipecard

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


/**
 * 通用的RecyclerView 的Adapter
 * Created by zhangxutong .
 * Date: 16/03/11
 */
abstract class CommonAdapter<T>(
        open var dataList: ArrayList<T> = ArrayList(),
) : RecyclerView.Adapter<CommonViewHolder<T>>() {

    protected var viewGroup: ViewGroup? = null //add by zhangxutong 2016 08 05 ,for 点击事件为了兼容HeaderView FooterView 的Adapter

    var onItemClickListener: OnItemClickListener<T>? = null

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder<T> {
        //add by zhangxutong 2016 08 05 begin ,for 点击事件为了兼容HeaderView FooterView 的Adapter
        if (null == viewGroup) {
            viewGroup = parent
        }
        //setListener(parent, viewHolder, viewType);
        //add by zhangxutong 2016 08 05 end ,for 点击事件为了兼容HeaderView FooterView 的Adapter
        return newViewHolder(parent, viewType)
    }

    abstract fun newViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder<T>

    protected open fun getPosition(viewHolder: CommonViewHolder<T>): Int {
        return viewHolder.bindingAdapterPosition
    }

    protected open fun enableItemListeners(viewType: Int): Boolean {
        return true
    }

    protected fun setListener(parent: ViewGroup?, viewHolder: CommonViewHolder<T>, viewType: Int) {
        if (!enableItemListeners(viewType)) return
        viewHolder.itemView.setOnClickListener { v ->
            if (onItemClickListener != null) {
                val position = getPosition(viewHolder)
                onItemClickListener!!.onItemClick(parent, v, dataList[position], position)
            }
        }
        viewHolder.itemView.setOnLongClickListener { v ->
            if (onItemClickListener != null) {
                val position = getPosition(viewHolder)
                return@setOnLongClickListener onItemClickListener!!.onItemLongClick(parent, v, dataList[position], position)
            }
            false
        }
    }

    final override fun onBindViewHolder(holder: CommonViewHolder<T>, position: Int) {
        //add by zhangxutong 2016 08 05 begin 点击事件为了兼容HeaderView FooterView 的Adapter，所以在OnBindViewHolder里，其实性能没有onCreate好
        setListener(position, holder)
        //add by zhangxutong 2016 08 05 end
        holder.dispatch(this, dataList[position], position)
    }

    //add by zhangxutong 2016 08 05 begin 点击事件为了兼容HeaderView FooterView 的Adapter，所以在OnBindViewHolder里，其实性能没有onCreate好
    protected fun setListener(position: Int, viewHolder: CommonViewHolder<T>) {
        if (!enableItemListeners(getItemViewType(position))) return
        viewHolder.itemView.setOnClickListener { v ->
            onItemClickListener?.onItemClick(viewGroup, v, dataList[position], position)
        }
        viewHolder.itemView.setOnLongClickListener { v ->
            if (onItemClickListener != null) {
                val position1 = getPosition(viewHolder)
                return@setOnLongClickListener onItemClickListener!!.onItemLongClick(viewGroup, v, dataList[position1], position1)
            }
            false
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    /**
     * 删除数据
     *
     * @param index index
     */
    @SuppressLint("NotifyDataSetChanged")
    fun remove(index: Int) {
        if (dataList.size > index && index > -1) {
            dataList.removeAt(index)
            notifyDataSetChanged()
        }
    }

    /**
     * 加载更多数据
     *
     * @param list list
     */
    @SuppressLint("NotifyDataSetChanged")
    fun addDataList(list: List<T>?) {
        if (!list.isNullOrEmpty()) {
            if (this.dataList.isNotEmpty()) {
                dataList.addAll(list)
            }
            else {
                this.dataList = ArrayList(list)
            }
            notifyDataSetChanged()
        }
    }

    /**
     * 刷新数据，初始化数据
     *
     * @param list list
     */
    @SuppressLint("NotifyDataSetChanged")
    open fun notifyDataList(list: List<T>?) {
        if (list.isNullOrEmpty()) {
            dataList.clear()
        }
        else {
            dataList = ArrayList(list)
        }
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T? {
        if (position > -1 && dataList.size > position) {
            return dataList[position]
        }
        return null
    }
}
