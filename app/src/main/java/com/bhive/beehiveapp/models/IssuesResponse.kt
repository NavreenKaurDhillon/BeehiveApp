package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

data class IssuesResponse (

    @SerializedName("id"           ) var id          : Int,
    @SerializedName("society_id"   ) var societyId   : Int,
    @SerializedName("reference_id" ) var referenceId : String,
    @SerializedName("images"       ) var images      : ArrayList<String> = arrayListOf(),
    @SerializedName("status"       ) var status      : Int,
    @SerializedName("title"        ) var title       : String,
    @SerializedName("description"   ) var description  : String,
    @SerializedName("user_id"       ) var userId       : Int,
    @SerializedName("created_at"    ) var createdAt    : String,
    @SerializedName("reported_date" ) var reportedDate : String,
    @SerializedName("complainant"   ) var complainant  : String,
    @SerializedName("floor_number"  ) var floorNumber  : String,

)