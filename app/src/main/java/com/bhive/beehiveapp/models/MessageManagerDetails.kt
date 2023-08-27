package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MessageManagerDetails (

    @SerializedName("message"  ) var message  : String,
    @SerializedName("response" ) var response : MessageManagerData

):Serializable

data class MessageManagerData
 (

    @SerializedName("id"           ) var id         : Int,
    @SerializedName("message"      ) var message    : String,
    @SerializedName("send_by"      ) var sendBy     : Int,
    @SerializedName("created_at"   ) var createdAt  : String,
    @SerializedName("is_read"      ) var isRead     : Int,
    @SerializedName("send_by_name" ) var sendByName : String

)