package com.yanyu.libs.swipecard

import android.content.Context
import android.util.TypedValue

class SwipeConfig private constructor(
        val maxShowCount: Int, //屏幕上最多同时显示几个Item
        val scaleGap: Float, //每一级Scale相差0.05f，translationY相差7dp左右
        val transGapDimenOfy: Int, // translationY相差7dp左右
) {

    @Suppress("unused")
    class Builder(private val context: Context) {

        private var maxShowCount: Int = 4
        private var scaleGap = 0.05F
        private var transGapDimenOfy = 15F

        private fun wrapTransGapDimenOfy(value: Float): Float {
            return TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    value,
                    context.resources.displayMetrics,
            )
        }

        fun setMaxShowCount(maxShowCount: Int): Builder {
            this.maxShowCount = maxShowCount
            return this
        }

        fun setScaleGap(scaleGap: Float): Builder {
            this.scaleGap = scaleGap
            return this
        }

        fun setTransOfyGap(transOfyGapDimen: Float): Builder {
            this.transGapDimenOfy = transOfyGapDimen
            return this
        }

        fun build(): SwipeConfig {
            val transOfyGap = wrapTransGapDimenOfy(transGapDimenOfy).toInt()
            return SwipeConfig(maxShowCount, scaleGap, transOfyGap)
        }
    }
}
