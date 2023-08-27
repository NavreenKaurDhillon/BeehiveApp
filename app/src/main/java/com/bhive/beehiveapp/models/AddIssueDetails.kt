package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddIssueDetails (

    @SerializedName("message"  ) var message  : String,
    @SerializedName("response" ) var response : AddIssueResponse

) : Serializable