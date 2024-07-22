package com.yanyu.demoapp

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 16/12/18.
 */
class SwipeCardBean(var position: Int, var url: String, var name: String) {

    companion object {

        fun initDataList(): ArrayList<SwipeCardBean> {
            val list: ArrayList<SwipeCardBean> = ArrayList(8)
            var i = 1
            list.add(SwipeCardBean(i++, "http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", "张"))
            list.add(SwipeCardBean(i++, "http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童"))
            list.add(SwipeCardBean(i++, "http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type"))
            list.add(SwipeCardBean(i++, "http://www.kejik.com/image/1460343965520.jpg", "多种type"))
            list.add(SwipeCardBean(i++, "http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type"))
            list.add(SwipeCardBean(i++, "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"))
            list.add(SwipeCardBean(i++, "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"))
            list.add(SwipeCardBean(i, "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"))
            return list
        }
    }
}
