package com.yanyu.libs.swipecard

import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.ItemTouchHelper.UP


object SwipeConstant {

    @JvmStatic
    val LR: Int = LEFT or RIGHT

    @JvmStatic
    val UD: Int = DOWN or UP

    @JvmStatic
    val ALL: Int = LR or UD
}
