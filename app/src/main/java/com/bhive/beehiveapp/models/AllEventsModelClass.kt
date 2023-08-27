package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

data class AllEventsModelClass (

    @SerializedName("message"  ) var message  : String?             = null,
    @SerializedName("response" ) var response : ArrayList<AllEventsResponse> = arrayListOf()

) : java.io.Serializable
{

    data class AllEventsResponse (

        @SerializedName("id"          ) var id          : Int?    = null,
        @SerializedName("agenda"      ) var agenda      : String? = null,
        @SerializedName("description" ) var description : String? = null,
        @SerializedName("event_date"  ) var eventDate   : Long?    = null,
        @SerializedName("created_by"  ) var createdBy   : String? = null

    )
}