package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

data class NotificationsData  (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("source_id"  ) var sourceId  : Int?    = null,
    @SerializedName("type"       ) var type      : Int?    = null,
    @SerializedName("message"    ) var message   : String? = null,
    @SerializedName("is_read"    ) var isRead    : Int?    = null,
    @SerializedName("title"      ) var title     : String? = null,
    @SerializedName("created_at" ) var createdAt : Long?    = null

)