package com.capstone.empoweru.data.dummy

import com.capstone.empoweru.data.dummy.CategoryItem.*

enum class CategoryItem(val value: String) {
    TRENDING("Trending"),
    LOCATION("Terdekat dengan Lokasi"),
    FOOD("Kuliner"),
    FASHION("Busana"),
    SHOP("Pertokoan"),
    BUSINESS("Agrabisnis")
}

fun getCategory(): List<CategoryItem> {
    return listOf(
        TRENDING,
        LOCATION,
        FOOD,
        FASHION,
        SHOP,
        BUSINESS
    )
}