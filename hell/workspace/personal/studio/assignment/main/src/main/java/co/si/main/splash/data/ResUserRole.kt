package co.si.main.splash.data

data class ResUserRole(
    val error: String,
    val httpStatus: Int,
    val message: String,
    val result: Result?,
    val successful: Boolean,
    val timestamp: String
)

data class Result(
    val createdAt: String,
    val desc: String,
    val externalRoleType: Int,
    val externalUser: String,
    val id: Int,
    val role: String,
    val subscriberId: Int,
    val updatedAt: String
)