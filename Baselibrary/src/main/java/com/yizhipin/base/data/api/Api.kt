package com.yizhipin.usercenter.data.api

/**
 * Created by ${XiLei} on 2018/7/27.
 */
interface Api {
    companion object {

        const val GET_CODE = "api/WebUser/Sms" //获取验证码
        const val REGISTER = "api/WebUser" //注册
        const val LOGIN = "api/WebUser/Login" //登录
        const val RESET_PWD = "api/WebUser/ResetPassword" //重置密码
        const val EDIT_USER_INFO = "api/WebUser"//编辑用户信息
        const val IMAGE_ADDRESS = "api/OSS/OSSCredentials"//获取图片地址
        const val DEFAULT_STORE = "api/Store/GetByLatlng"//附近门店
        const val NEWS = "api/Information/Page" //资讯
        const val UNREAD_NEWS_COUNT = "api/UserMsg/UnRead" //获取未读消息数
        const val FEE_RECORD_LIST = "api/FeeRecord/List" //资金记录
        const val INTEGRAL_LIST = "api/ScoreRecord/Page" //积分记录
        const val INVITATION_LIST = "api/UserCenter/Request" //邀请人列表
        const val INVITATION_ADD = "api/UserCenter/SetParent" //填写邀请码
        const val DRESS_CATEGORY = "api/ClothesCatagory/List" //服装分类
        const val DRESS_LIST = "api/Clothes/Page" //服装列表
        const val GOODS_DETAIL = "api/Clothes"//服装详情
        const val GOODS_NORM = "api/ClothesNorm/List"//服装规格
        const val FOLLOW_DRESS = "api/ClothesAttention"//关注、取消关注服装
        const val SHOP_LIST = "api/Store/Page"//门店列表
        const val SHOP_DETAILS = "api/Store"//门店详情
        const val SHOP_BANNER = "api/StoreBanner/List"//获取门店banner
        const val SHOP_FOLLOW = "api/StoreAttention"//门店关注、取消关注
        const val HOT_MEAL = "api/PhotoPackage/List"//门店热门套餐
        const val TIME_TEACHER = "api/TeacherInfo/List"//门店时光老师列表
        const val EVALUATE = "api/PhotoPackageEva/List"//门店、套餐 评价
        const val MEAL_LIST = "api/PhotoPackage/Page" //套餐列表
        const val MEAL_DETAILS = "api/PhotoPackage" //套餐详情
        const val MEAL_FOLLOW = "api/PackageAttention"//套餐关注、取消关注
        const val BASIC_SERVICES = "api/StoreService/List"//基础服务
        const val CLOUDLISK_LIST = "api/Folder/Page"//时光云盘
        const val CLOUDLISK_IMAGE_LIST = "api/FolderFile/Page"//时光云盘二级
        const val DRESS_TIME_SUPERMARKET = "api/MarkerProductCatagory/List" //时光超市分类
        const val DRESS_TIME_SUPERMARKET_LIST = "api/MarkerProduct/Page" //时光超市列表
        const val DRESS_TIME_SUPERMARKET_DETAILS = "api/MarkerProduct" //时光超市详情
        const val MARKET_FOLLOW = "api/MarkerProduct/Attention"//时光超市关注、取消关注
        const val INTEGRAL_MALL_LIST = "api/ScoreProduct/Page" //积分商城列表
        const val INTEGRAL_MALL_DETAILS = "api/ScoreProduct" //积分商城详情
        const val SCENIC_LIST = "api/Attractions/Page" //景点列表
        const val SCENIC_DETAILS = "api/Attractions" //景点详情
        const val SCENIC_FOLLOW = "api/Attractions/Attention"//景点关注、取消关注
        const val MEAL_SENIC = "api/PackageAttraction/PhotoPackage"//景点相关套餐
        const val CAMERAMAN_LIST = "api/TeacherInfo/Page" //摄影师列表
        const val CAMERAMAN_DETAILS = "api/TeacherInfo" //摄影师详情
        const val CAMERAMAN_FOLLOW = "api/TeacherAttention"//摄影师关注、取消关注
        const val CAMERAMAN_WORKS = "api/TeacherWorks/Page"//摄影师作品
        const val STAR = "api/Star/Star"//明星榜
        const val INTARACTION_LIST = "api/InteractiveBar/Page"//互动吧列表
        const val INTARACTION_DETAILS = "api/InteractiveBar"//互动吧详情
        const val GIVE_LIKE = "api/InteractiveBar/Zan"//点赞
        const val COMMENT = "api/InteractiveBar/Eva"//评论
        const val RELEVANCE_USER = "api/UserRelated/List"//关联用户
        const val ADD_RELEVANCE_USER = "api/UserRelated"//关联用户\删除关联用户\更改关联用户
        const val MEAL_ORDER = "api/Order" //套餐下单\套餐订单详情
        const val ADD_BRIDE_INFO = "api/Order/Info" //添加新人信息
        const val ADD_CAMERAMAN = "api/Order/Teacher" //添加摄影师
        const val RECHERGE = "api/UserCenter/Recharge" //余额充值
        const val RECHERGE_CASH_PLEDGE = "api/UserDeposit" //押金充值
        const val CASH_PLEDGE = "api/UserDeposit/User" //押金


        const val BIND_MOBILE = "api/WebUser/BindingMobile"//编辑用户信息
        const val ADDRESS_LIST = "api/UserAddress/List"//收货地址列表
        const val ADD_ADDRESS = "api/UserAddress"//新增收货地址 \  修改收货地址 \  删除收货地址
        const val SET_DEFAULT_ADDRESS = "api/UserAddress/SetDefault"//设置默认收货地址
        const val BANNER = "api/Banner/List"//获取banner
        const val CATEGORY = "api/Product/Category"//一级分类
        const val CATEGORY_SECOND = "api/Product/Category/List"//二级分类
        const val GOODS_LIST = "api/Product/Page"//商品列表
        const val EVALUATE_NEW = "api/ProductEva/New"//最新评价
        const val REPORT_NEW = "api/Experience/Product"//最新体验报告
        const val EVALUATE_LIST = "api/ProductEva/Page"//评价列表
        const val REPORT_LIST = "api/Experience/Page"//体验报告列表
        const val GIVE_LIKE_REPORT = "api/Experience/Zan"//点赞体验报告
        const val SHOP_DETAIL = "api/Shop"//店铺详情
        const val COLLECT_SHOP = "api/ShopCollection"//收藏店铺
        const val COMPLAIN_SHOP = "api/ShopComplaint"//举报投诉
        const val DEFAULT_ADDRESS = "api/UserAddress/Default"//获取默认地址
        const val SUBMIT_ORDER = "api/Order"//提交订单
        const val SUBMIT_ORDER_RESIDE = "api/Order/Homestay"//提交订单(一品小住)
        const val ADD_CART = "api/ShopCart"//加入购物车
        const val CART_LIST = "api/ShopCart/List"//购物车列表
        const val COLLECT_GOOD = "api/ProductCollection"//收藏商品
        const val DELETE_CART_GOODS = "api/ShopCart"//删除购物车商品
        const val CART_COUNT = "api/ShopCart/Count"//获取购物车数量
        const val HOT_GOODS_LIST = "api/Attractions/List"//热门商品景点
        const val SET_PAY_PWD = "api/WebUser/PayPassword/Set"//设置支付密码
        const val UPDATE_PAY_PWD = "api/WebUser/PayPassword/Change"//修改支付密码
        const val RESET_PAY_PWD = "api/WebUser/PayPassword/Reset"//重置支付密码
        const val COUPON_LIST = "api/UserCoupon/Page"//我的优惠券
        const val RED_PACKET_LIST = "api/UserRed/Page"//红包记录
        const val RED_BALANCE = "api/UserRed/Total"//红包总额
        const val ORDER_LIST = "api/Order/Page"//订单列表
        const val ORDER_CANCEL = "api/Order"//取消订单
        const val COLLECT_GOODS = "api/ProductCollection/Page"//收藏的商品列表
        const val COLLECT_SHOP_LIST = "api/ShopCollection/Page"//收藏的店铺列表
        const val GENERALIZE_LIST = "api/Investment/Page"//推广中的商品列表
        const val GENERALIZE_DETAILS = "api/Investment"//推广中的商品详情
        const val GENERALIZE_GROUP_DETAILS = "api/Investment/Group/Detail"//投资团详情
        const val PAY_PERSONAGE = "api/Investment/User"//个人出价投资
        const val GENERALIZE_INVEST_AMOUNT = "api/InvestmentIncome/Total"//投资收益金额
        const val GENERALIZE_INVEST_LIST = "api/InvestmentIncome/Record"//投资推广列表
        const val INVEST_DETAILS_LIST = "api/InvestmentIncome/List"//投资明细列表
        const val INVEST_DETAILS = "api/InvestmentIncome/My/Detail"//投资详情
        const val SEARCH_KEYWORD = "api/Product/SearchKeyWords"//关键字
        const val END_TIME = "api/Investment/EndTime"//竞价结束时间
        const val CROWDORDER_LIST = "api/Tuan/List"//拼单列表
        const val SHARE_BILL_LIST = "api/Tuan/NearBy"//附近品团列表
    }
}