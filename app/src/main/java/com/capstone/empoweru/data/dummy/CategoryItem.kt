package com.capstone.empoweru.data.dummy

import com.capstone.empoweru.data.dummy.CategoryItem.*

enum class CategoryItem(val value: String) {
    TRENDING("Trending"),
    FOOD("Kuliner"),
    FASHION("Busana"),
    SHOP("Pertokoan"),
    BUSINESS("Agrabisnis"),
    OTHER("Lainnya")
}

fun getCategory(): List<CategoryItem> {
    return listOf(
        TRENDING,
        FOOD,
        FASHION,
        SHOP,
        BUSINESS,
        OTHER
    )
}