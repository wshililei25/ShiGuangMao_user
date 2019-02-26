package com.yizhipin.base.data.response

/**
 * Created by ${XiLei} on 2018/10/5.
 *
 * 分页数据
 */
data class Paging(
    val currentPage: Int,
    val pageSize: Int,
//    val query: Query,
    val totalPage: Int,
    val totalSize: Int
)

/*
data class Query(
    val uid: Int
)*/
