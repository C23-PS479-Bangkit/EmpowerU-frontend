package com.capstone.empoweru.data.dummy

data class Rating(val value: Int, val description: String)

val ratings = listOf(
    Rating(1, "Tidak Menarik"),
    Rating(2, "Kurang menarik"),
    Rating(3, "Biasa saja"),
    Rating(4, "Cukup Menarik"),
    Rating(5, "Sangat Menarik")
)
