package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

class NotificationsCountModelClass (

    @SerializedName("message"  ) var message  : String? = null,
    @SerializedName("response" ) var response : Int?    = null

)