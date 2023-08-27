package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

data class Messages (

    @SerializedName("id"           ) var id         : Int,
    @SerializedName("message"      ) var message    : String,
    @SerializedName("send_by"      ) var sendBy     : Int,
    @SerializedName("created_at"   ) var createdAt  : String,
    @SerializedName("is_read"      ) var isRead     : Int,
    @SerializedName("send_by_name" ) var sendByName : String

)
