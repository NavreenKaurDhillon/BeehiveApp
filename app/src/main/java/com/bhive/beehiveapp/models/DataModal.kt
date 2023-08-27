package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

data class DataModal
    (
    @SerializedName("message"  ) var message  : String?,
    @SerializedName("response" ) var response : Response
            )