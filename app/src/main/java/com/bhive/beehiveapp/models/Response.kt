package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

data class Response (
    @SerializedName("id"               ) var id             : Int?    = null,
    @SerializedName("first_name"       ) var firstName      : String? = null,
    @SerializedName("last_name"        ) var lastName       : String? = null,
    @SerializedName("email"            ) var email          : String? = null,
    @SerializedName("phone"            ) var phone          : String? = null,
    @SerializedName("image"            ) var image          : String? = null,
    @SerializedName("token_expired_at" ) var tokenExpiredAt : String? = null,
    @SerializedName("role"             ) var role           : Int?    = null,
    @SerializedName("society_id"       ) var societyId      : Int?    = null,
    @SerializedName("floor_number"     ) var floorNumber    : String? = null,
    @SerializedName("status"           ) var status         : Int?    = null,
    @SerializedName("is_deleted"       ) var isDeleted      : Int?    = null,
    @SerializedName("society"          ) var society        : String? = null,
    @SerializedName("primary_color"    ) var primaryColor   : String? = null,
    @SerializedName("secondary_color"  ) var secondaryColor : String? = null,
    @SerializedName("logo"             ) var logo           : String? = null,
    @SerializedName("device_token"     ) var deviceToken    : String? = null,
    @SerializedName("access_token"     ) var accessToken    : String? = null,
    @SerializedName("remember_token"   ) var rememberToken  : String? = null
)