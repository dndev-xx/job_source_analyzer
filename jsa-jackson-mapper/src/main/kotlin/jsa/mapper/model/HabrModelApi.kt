package jsa.mapper.model

data class ItemHabr(
    val companyName: String?,
    val title: String?,
    val salary: String?,
    val description: String?,
    val created: String?,
    val location: String?,
    val link: String?,
    val contacts: String?
)

data class HabrModelApi(
    val items: ArrayList<ItemHabr>
)