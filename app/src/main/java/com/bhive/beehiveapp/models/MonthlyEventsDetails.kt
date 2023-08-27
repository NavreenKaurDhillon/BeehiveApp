package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

data class MonthlyEventsDetails (

    @SerializedName("message"  ) var message  : String,
    @SerializedName("response" ) var response : ArrayList<MonthlyEventsData>

)