package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProfileDetails (

    @SerializedName("message"  ) var message  : String,
    @SerializedName("response" ) var response : ProfileData

) : Serializable {

    data class ProfileData (

        @SerializedName("first_name"           ) var firstName          : String? = null,
        @SerializedName("last_name"            ) var lastName           : String? = null,
        @SerializedName("email"                ) var email              : String? = null,
        @SerializedName("phone"                ) var phone              : String? = null,
        @SerializedName("image"                ) var image              : String? = null,
        @SerializedName("password_reset_token" ) var passwordResetToken : String? = null,
        @SerializedName("token_expired_at"     ) var tokenExpiredAt     : String? = null,
        @SerializedName("role"                 ) var role               : Int?    = null,
        @SerializedName("society_id"           ) var societyId          : Int?    = null,
        @SerializedName("floor_number"         ) var floorNumber        : String? = null,
        @SerializedName("created_by"           ) var createdBy          : String? = null,
        @SerializedName("deleted_by"           ) var deletedBy          : String? = null,
        @SerializedName("status"               ) var status             : Int?    = null,
        @SerializedName("is_deleted"           ) var isDeleted          : Int?    = null,
        @SerializedName("deleted_at"           ) var deletedAt          : String? = null,
        @SerializedName("last_login_at"        ) var lastLoginAt        : String? = null,
        @SerializedName("society"              ) var society            : String? = null,
        @SerializedName("primary_color"        ) var primaryColor       : String? = null,
        @SerializedName("secondary_color"      ) var secondaryColor     : String? = null,
        @SerializedName("logo"                 ) var logo               : String? = null

    )
}