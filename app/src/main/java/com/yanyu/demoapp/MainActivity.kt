package com.yanyu.demoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import com.squareup.picasso.Picasso
import com.yanyu.demoapp.databinding.ActivityMainBinding
import com.yanyu.demoapp.databinding.ItemSwipeCardBinding
import com.yanyu.libs.baseframe.ktx.inflateItem
import com.yanyu.libs.baseframe.ui.BaseActivity
import com.yanyu.libs.klog.KLog
import com.yanyu.libs.swipecard.CommonAdapter
import com.yanyu.libs.swipecard.CommonViewHolder
import com.yanyu.libs.swipecard.OverLayCardLayoutManager
import com.yanyu.libs.swipecard.SwipeCallback
import com.yanyu.libs.swipecard.SwipeConfig

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun createViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initData() {
        //初始化配置
        val swipeConfig: SwipeConfig = SwipeConfig.Builder(this).build()
        binding.rv.layoutManager = OverLayCardLayoutManager(swipeConfig)
        val beanList: ArrayList<SwipeCardBean> = SwipeCardBean.initDataList()
        KLog.i(logTag, "大小 " + beanList.size)
        val swipeAdapter = object : CommonAdapter<SwipeCardBean>(beanList) {
            val size = beanList.size

            override fun newViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder<SwipeCardBean> {
                return object : CommonViewHolder<SwipeCardBean>(parent.inflateItem(R.layout.item_swipe_card)) {

                    override fun dispatch(adapter: CommonAdapter<SwipeCardBean>, data: SwipeCardBean, position: Int) {
                        val binding: ItemSwipeCardBinding = ItemSwipeCardBinding.bind(itemView)
                        binding.tvName.text = data.name
                        val sizeText: String = "${data.position}" + " /" + size
                        binding.tvPrecent.text = sizeText
                        Picasso.with(binding.root.context).load(data.url).into(binding.iv)
                    }
                }
            }
        }
        binding.rv.adapter = swipeAdapter
        val builder = SwipeCallback.Builder<SwipeCardBean>(binding.rv) // 构造器
            .setAdapter(swipeAdapter) // 适配器
            .setSwipeConfig(swipeConfig) // 卡片配置
            .setSwipeLeftRight() // 设置左右滑动
            .setSwipeDirectionCallback(object : SwipeCallback.SwipeDirectionCallback<SwipeCardBean>() {
                override fun onLeft(data: SwipeCardBean) {
                    KLog.i(logTag, "左滑")
                }

                override fun onRight(data: SwipeCardBean) {
                    KLog.i(logTag, "右滑")
                }
            })

        val itemTouchHelper = ItemTouchHelper(SwipeCallback(builder))
        itemTouchHelper.attachToRecyclerView(binding.rv)
    }

    override fun initListeners() {
    }

    override fun initViews() {
    }
}
