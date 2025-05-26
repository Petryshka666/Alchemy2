package com.example.alchemy2.data

data class Element(
    val id: Int,
    val name: String,
    val imageRes: Int,//айди ресурса изображения
    var offsetX: Float = 0f,//X
    var offsetY: Float = 0f//Y
)