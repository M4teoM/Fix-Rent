package edu.javeriana.fixup.data.network.dto

import com.google.gson.annotations.SerializedName

data class ArticleDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
    @SerializedName("category")
    val category: String? = null
)
