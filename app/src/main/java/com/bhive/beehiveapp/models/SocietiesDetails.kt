package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SocietiesDetails (

    @SerializedName("message" ) var message : String,
    @SerializedName("data"    ) var data    : ArrayList<SocietiesData>

) : Serializable {

    data class SocietiesData (

        @SerializedName("id"          ) var id         : Int,
        @SerializedName("name"        ) var name       : String,
        @SerializedName("address"     ) var address    : String,
        @SerializedName("floors"      ) var floors     : Int,
        @SerializedName("logo"        ) var logo       : String,
        @SerializedName("created_by"  ) var createdBy  : Int,
        @SerializedName("status"      ) var status     : Int,
        @SerializedName("is_deleted"  ) var isDeleted  : Int,
        @SerializedName("is_approved" ) var isApproved : Int,
        @SerializedName("approved_by" ) var approvedBy : Int,
        @SerializedName("created_at"  ) var createdAt  : String,
        @SerializedName("updated_at"  ) var updatedAt  : String,
        @SerializedName("deleted_at"  ) var deletedAt  : String

    )
}