package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

data class BuildingRulesModelClass(

    @SerializedName("message"  ) var message  : String?             = null,
    @SerializedName("response" ) var response : ArrayList<RulesResponse> = arrayListOf()

) : java.io.Serializable
{
    data class RulesResponse (

        @SerializedName("id"          ) var id          : Int?    = null,
        @SerializedName("rule"        ) var rule        : String? = null,
        @SerializedName("description" ) var description : String? = null,
        @SerializedName("created_by"  ) var createdBy   : Int?    = null,
        @SerializedName("created_at"  ) var createdAt   : String? = null,
        @SerializedName("added_by"    ) var addedBy     : String? = null

    )

}


