package com.yanyu.libs.swipecard

import android.annotation.SuppressLint
import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.sqrt

open class SwipeCallback<T> constructor(builder: Builder<T>) : ItemTouchHelper.SimpleCallback(builder.dragDirs, builder.swipeDirs) {

    @Suppress("MemberVisibilityCanBePrivate")
    protected var recyclerView: RecyclerView = builder.recyclerView

    @Suppress("MemberVisibilityCanBePrivate")
    protected var adapter: CommonAdapter<T> = builder.adapter!!

    @Suppress("MemberVisibilityCanBePrivate")
    protected val swipeConfig: SwipeConfig = builder.swipeConfig!!

    @Suppress("MemberVisibilityCanBePrivate")
    protected var swipeDirectionCallback: SwipeDirectionCallback<T>? = builder.swipeDirectionCallback

    @Suppress("MemberVisibilityCanBePrivate")
    protected var loop: Boolean = builder.loop

    private val threshold: Float
        // 水平方向是否可以被回收掉的阈值 //2016 12 26 考虑 探探垂直上下方向滑动，不删除卡片，这里参照源码写死0.5f
        get() = recyclerView.width *  /*getSwipeThreshold(viewHolder)*/0.5f

    @Suppress("RedundantOverride")
    override fun isItemViewSwipeEnabled(): Boolean {
        // true 代表可以滑动删除当前，默认为true
        return super.isItemViewSwipeEnabled()
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val removeData: T = adapter.dataList.removeAt(viewHolder.layoutPosition)
        // ★实现循环的要点
        if (loop) {
            adapter.dataList.add(0, removeData)
        }
        adapter.notifyDataSetChanged()
        swipeDirectionCallback?.dispatch(direction, removeData)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        //Log.e("SwipeCard", "onChildDraw()  viewHolder = [" + viewHolder + "], dX = [" + dX + "], dY = [" + dY + "], actionState = [" + actionState + "], isCurrentlyActive = [" + isCurrentlyActive + "]");
        //人人影视的效果
        //if (isCurrentlyActive) {
        //先根据滑动的dx dy 算出现在动画的比例系数fraction
        val swipeValue = sqrt((dX * dX + dY * dY).toDouble())
        var fraction = swipeValue / threshold
        //边界修正 最大为1
        if (fraction > 1) {
            fraction = 1.0
        }
        //对每个ChildView进行缩放 位移
        val childCount = recyclerView.childCount
        for (i in 0 until childCount) {
            val child = recyclerView.getChildAt(i)
            //第几层,举例子，count =7， 最后一个TopView（6）是第0层，
            val level = childCount - i - 1
            if (level > 0) {
                val scaleX = (1 - swipeConfig.scaleGap * level + fraction * swipeConfig.scaleGap).toFloat()
                child.scaleX = scaleX
                if (level < swipeConfig.maxShowCount - 1) {
                    child.scaleY = scaleX
                    child.translationY = (swipeConfig.transGapDimenOfy * level - fraction * swipeConfig.transGapDimenOfy).toFloat()
                }
                else {
                    //child.setTranslationY((float) (mTranslationYGap * (level - 1) - fraction * mTranslationYGap));
                }
            }
        }
    }

    abstract class SwipeDirectionCallback<T> {

        internal fun dispatch(direction: Int, data: T) {
            when (direction) {
                ItemTouchHelper.LEFT -> onLeft(data)
                ItemTouchHelper.RIGHT -> onRight(data)
                ItemTouchHelper.UP -> onUp(data)
                ItemTouchHelper.DOWN -> onDown(data)
                else -> throw IllegalArgumentException("direction must be LEFT, RIGHT, UP or DOWN")
            }
        }

        open fun onLeft(data: T) {

        }

        open fun onRight(data: T) {

        }

        open fun onUp(data: T) {

        }

        open fun onDown(data: T) {

        }
    }

    /**
     * 不直接生成Build是考虑到 SwipeCallback 可能会被继承，
     * 所以将Builder放在构造函数中
     */
    @Suppress("unused")
    class Builder<T>(internal val recyclerView: RecyclerView) {

        internal var adapter: CommonAdapter<T>? = null
        internal var swipeConfig: SwipeConfig? = null
        internal var swipeDirectionCallback: SwipeDirectionCallback<T>? = null
        internal var loop: Boolean = false
        internal var dragDirs: Int = 0
        internal var swipeDirs: Int = SwipeConstant.ALL

        fun setAdapter(adapter: CommonAdapter<T>): Builder<T> {
            this.adapter = adapter
            return this
        }

        fun setSwipeConfig(swipeConfig: SwipeConfig): Builder<T> {
            this.swipeConfig = swipeConfig
            return this
        }

        fun setSwipeDirectionCallback(swipeDirectionCallback: SwipeDirectionCallback<T>): Builder<T> {
            this.swipeDirectionCallback = swipeDirectionCallback
            return this
        }

        fun setLoop(loop: Boolean): Builder<T> {
            this.loop = loop
            return this
        }

        fun setDragDirs(dragDirs: Int): Builder<T> {
            this.dragDirs = dragDirs
            return this
        }

        fun setSwipeDirs(swipeDirs: Int): Builder<T> {
            this.swipeDirs = swipeDirs
            return this
        }

        fun setSwipeLeftRight(): Builder<T> {
            this.swipeDirs = SwipeConstant.LR
            return this
        }

        fun setSwipeUpDown(): Builder<T> {
            this.swipeDirs = SwipeConstant.UD
            return this
        }
    }
}
