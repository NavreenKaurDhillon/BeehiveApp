package com.bhive.beehiveapp.models

import com.google.gson.annotations.SerializedName

data class IssuesDetails (

    @SerializedName("message"             ) var message             : String,
    @SerializedName("response"            ) var response            : ArrayList<IssuesResponse>,
    @SerializedName("totalIssuesCount"      ) var totalIssuesCount      : Int,
    @SerializedName("openIssuesCount"       ) var openIssuesCount       : Int,
    @SerializedName("resolvedIssuesCount"   ) var resolvedIssuesCount   : Int,
    @SerializedName("inProgressIssuesCount" ) var inProgressIssuesCount : Int,
    @SerializedName("myIssuesCount"         ) var myIssuesCount         : Int,
    @SerializedName("meta"                ) var meta                : IssuesMeta

)