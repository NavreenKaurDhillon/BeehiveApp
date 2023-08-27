package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

data class GetIssueDetails (

    @SerializedName("message"  ) var message  : String,
    @SerializedName("response" ) var response : GetIssueData

)