package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EventsDetails  (

    @SerializedName("message"  ) var message  : String,
    @SerializedName("response" ) var response : ArrayList<EventsData> = arrayListOf()

):  Serializable
{
    data class EventsData (

        @SerializedName("id"          ) var id          : Int,
        @SerializedName("agenda"      ) var agenda      : String,
        @SerializedName("description" ) var description : String,
        @SerializedName("event_date"  ) var eventDate   : Long,
        @SerializedName("created_by"  ) var createdBy   : String

    )
}