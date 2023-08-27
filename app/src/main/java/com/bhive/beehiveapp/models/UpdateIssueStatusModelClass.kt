package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

data class UpdateIssueStatusModelClass (

    @SerializedName("message"  ) var message  : String?   = null,
    @SerializedName("response" ) var response : UpdateStatusResponse? = UpdateStatusResponse()

):java.io.Serializable
{
    data class UpdateStatusResponse(

        @SerializedName("id"            ) var id           : Int?              = null,
        @SerializedName("society_id"    ) var societyId    : Int?              = null,
        @SerializedName("reference_id"  ) var referenceId  : String?           = null,
        @SerializedName("images"        ) var images       : ArrayList<String> = arrayListOf(),
        @SerializedName("status"        ) var status       : Int?              = null,
        @SerializedName("title"         ) var title        : String?           = null,
        @SerializedName("description"   ) var description  : String?           = null,
        @SerializedName("user_id"       ) var userId       : Int?              = null,
        @SerializedName("created_at"    ) var createdAt    : String?           = null,
        @SerializedName("reported_date" ) var reportedDate : String?           = null,
        @SerializedName("complainant"   ) var complainant  : String?           = null,
        @SerializedName("floor_number"  ) var floorNumber  : String?           = null

    )
}