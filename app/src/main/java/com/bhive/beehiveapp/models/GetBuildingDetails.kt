package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

data class GetBuildingDetails  (

    @SerializedName("message"  ) var message  : String,
    @SerializedName("response" ) var response : SocietiesDetails.SocietiesData

)