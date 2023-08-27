package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

data class BuildingFaciltiesModelClass (

    @SerializedName("message"  ) var message  : String?             = null,
    @SerializedName("response" ) var response : ArrayList<FaciltiesResponse> = arrayListOf()

) : java.io.Serializable {

data class FaciltiesResponse (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("name"       ) var name      : String? = null,
    @SerializedName("location"   ) var location  : String? = null,
    @SerializedName("close_time" ) var closeTime : String? = null,
    @SerializedName("open_time"  ) var openTime  : String? = null,
    @SerializedName("added_by"   ) var addedBy   : String? = null,
    @SerializedName("created_at" ) var createdAt : String? = null
)
}