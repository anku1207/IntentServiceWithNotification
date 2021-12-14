package `in`.cbslgroup.ucobank.model.response.appointment

import com.google.gson.annotations.SerializedName

data class BranchResponse(

	@field:SerializedName("branchList")
	val branchList: List<BranchListItem>? = null,

	@field:SerializedName("isError")
	val isError: Boolean? = null,

	@field:SerializedName("Message")
	val Message: String? = null,

	@field:SerializedName("branch")
	val branch: Branch? = null
)

data class BranchListItem(

	@field:SerializedName("BranchId")
	val branchId: Int? = null,

	@field:SerializedName("BranchName")
	val branchName: String? = null
)

data class Branch(

	@field:SerializedName("BranchId")
	val branchId: Int? = null,

	@field:SerializedName("BranchName")
	val branchName: Any? = null
)
