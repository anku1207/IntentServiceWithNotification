package `in`.cbslgroup.ucobank.model.response

import com.google.gson.annotations.SerializedName

data class DefaultResponse(

    @field:SerializedName("Error")
    val error: String? = null,

    @field:SerializedName("Message")
    val message: String? = null
)
