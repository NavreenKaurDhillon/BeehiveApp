package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

data class IssuesMeta (

    @SerializedName("page"    ) var page    : Int,
    @SerializedName("perPage" ) var perPage : Int,
    @SerializedName("totalPages" ) var totalPages : Int

)