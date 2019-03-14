package com.yizhipin.provider.router

/*
    模块路由 路径定义
 */
object RouterPath {
    //用户模块
    class UserCenter {
        companion object {
            const val PATH_LOGIN = "/userCenter/login"
            const val SET_PAY_PWD = "/userCenter/setPayPwd" //设置支付密码
            const val UPDATE_PAY_PWD = "/userCenter/updatePayPwd" //修改支付密码
            const val RESET_PAY_PWD = "/userCenter/resetPayPwd" //重置支付密码
            const val BALANCE = "/userCenter/balance" //余额
            const val UPDATE_LOGIN_PWD = "/userCenter/updateLoginPwd" //修改登录密码
        }
    }

    //商品模块
    class GoodsCenter {
        companion object {
            const val PATH_GOODS_CART = "/goodsCenter/cart"
            const val PATH_GOODS_DRESSSHOP = "/goodsCenter/dressShop"
            const val PATH_MEAL = "/goodsCenter/meal" //套餐
            const val PATH_CLOUD_DISK = "/goodsCenter/cloudDisk" //时光云盘
            const val PATH_TIME_SUPERMARKET = "/goodsCenter/timeSuperMarket" //时光超市
            const val PATH_INTEGRAL = "/goodsCenter/integral" //积分商城
            const val PATH_ORDER_MEAL_DETAILS = "/goodsCenter/orderMealDetails" //套餐订单详情
            const val PATH_MEAL_ORDER = "/goodsCenter/mealOrder" //下单
        }
    }

    //订单模块
    class OrderCenter {
        companion object {
            const val PATH_ORDER_CONFIRM = "/orderCenter/confirm"
            const val PATH_ORDER_DETAILS = "/orderCenter/details"
            const val PATH_ORDER_COUPON = "/orderCenter/coupon" //优惠券
            const val PATH_ORDER_RED = "/orderCenter/red" //现金红包
            const val PATH_ORDER_PAY = "/orderCenter/pay" //支付
        }
    }

    //支付模块
    class PayCenter {
        companion object {
            const val PATH_PAY_RECHARGE = "/payCenter/recharge"
            const val PATH_PAY_WITHDRAW = "/payCenter/withraw"
        }
    }

    //消息模块
    class MessageCenter {
        companion object {
            const val PATH_MESSAGE_PUSH = "/message/push"
            const val PATH_MESSAGE_ORDER = "/messageCenter/order"
        }
    }

    //店铺模块
    class ShopCenter {
        companion object {
            const val PATH_SHOP = "/shop/shop" //门店
            const val PATH_SHOP_DETAILS = "/shop/shopDetails" //
        }
    }
}
