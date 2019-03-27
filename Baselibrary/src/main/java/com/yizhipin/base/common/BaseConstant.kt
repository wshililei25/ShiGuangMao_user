package com.yizhipin.base.common

/**
 * Created by ${XiLei} on 2018/7/27.
 */
class BaseConstant {
    companion object {

        //接口地址
        const val SERVICE_ADDRESS = "https://nian.im/time-cat-api/"
        //SP表名
        const val TABLE_PREFS = "ShiGuangMao"
        //user id
        const val KEY_SP_USER_ID = "userId"
        //Token
        const val KEY_SP_TOKEN = "token"
        //图片服务器地址
        const val IMAGE_ADDRESS = "IMAGE_ADDRESS"

        //用户信息
        const val KEY_USER_INFO = "userInfo"
        //服装id
        const val KEY_DRESS_ID = "dressId"
        //女装头像
        const val KEY_DRESS_WOMEN_IMAGE = "dressWomenImage"
        //男装头像
        const val KEY_DRESS_MAN_IMAGE = "dressManImage"
        //服装规格
        const val KEY_DRESS_NORMS = "dressNorms"
        //库存
        const val KEY_DRESS_INVENTORY = "dressInventory"
        //是服装购买还是服装租借
        const val KEY_IS_BUY = "isBuy"
        //是否男装女装
        const val KEY_DRESS = "isDress"
        const val KEY_DRESS_POSITION = "dressPosition"
        //实际支付金额
        const val KEY_PAY_AMOUNT = "payAmount"
        //购买数量
        const val KEY_PAY_NUM = "payNum"
        //支付来源
        const val KEY_PAY_FROM = "payFrom"
        //收货地址id
        const val KEY_ADDRESS_ID = "addressId"
        //门店id
        const val KEY_SHOP_ID = "shopId"
        //门店名称
        const val KEY_SHOP_NAME = "shopName"
        //商品集合
        const val KEY_GOODS_LIST = "goodsList"
        //是否拼单  还是原价购买
        const val KEY_IS_PIN = "isPin"
        //摄影类型
        const val KEY_PHOTOGRAPH = "mealPhotograph"
        //套餐类型
        const val KEY_MEAL_STATUS = "mealStatus"
        //套餐id
        const val KEY_MEAL_ID = "mealId"
        //时光云盘类别
        const val KEY_CLOUD_DISK_STATUS = "cloudDiskStatus"
        //时光云盘item
        const val KEY_CLOUD_DISK_ITEM = "cloudDiskItem"
        //时光超市类别
        const val KEY_TIME_MARKET_CATEGORY = "timeMarketCategory"
        //时光超市id
        const val KEY_TIME_MARKET_ID = "timeMarketId"
        //积分商城id
        const val KEY_INTEGRAL_ID = "integralId"
        //景点id
        const val KEY_SCENIC_ID = "scenicId"
        //景点名称
        const val KEY_SCENIC_NAME = "scenicName"
        //景点图像
        const val KEY_SCENIC_IMAGE = "scenicImage"
        //景点金额
        const val KEY_SCENIC_AMOUNT = "scenicAmount"
        //老师id
        const val KEY_TEACHER_ID = "teacherId"
        //老师userId
        const val KEY_TEACHER_USER_ID = "teacherUserId"
        //互动吧类型
        const val KEY_INTERACTION_STATUS = "interactionStatus"
        //互动吧id
        const val KEY_INTERACTION_ID = "interactionId"
        //关联用户id
        const val KEY_RELEVANCE_ID = "relevanceId"
        //新增关联用户还是更新关联用户
        const val KEY_IS_ADD = "isAdd"
        //套餐订单id
        const val KEY_MEAL_ORDER_ID = "mealOrderId"
        //新人信息
        const val KEY_BRIDE_INFO = "brideInfo"
        //添加摄影师的信息
        const val KEY_ADD_CAMERAMAN = "addCameraman"
        const val KEY_ADD_CAMERAMAN_AMOUNT = "addCameramanAmount"
        const val KEY_ADD_CAMERAMAN_URL = "addCameramanUrl"
        //老师类型
        const val KEY_CAMERAMAN_TYPE = "cameramanType"
        //支付成功提示语
        const val KEY_PAY_CONTENT = "payContent"
        //余额充值还是押金充值
        const val KEY_IS_CASHPLEDGE = "isCashPledge"
        //资讯id
        const val KEY_INFORMATION_ID = "informationId"
        //老师预定下单返回的老师数据
        const val KEY_ORDER_TEACHER = "orderTeacher"
        //景点预定下单返回的景点数据
        const val KEY_ORDER_SCENIS = "orderScenic"
        //服装预定下单返回的服装数据
        const val KEY_ORDER_DRESS = "orderDress"
        //是提现还是退押金
        const val KEY_IS_CASH = "isCash"
        //是否预定
        const val KEY_IS_DESTINE = "destine"
    }
}