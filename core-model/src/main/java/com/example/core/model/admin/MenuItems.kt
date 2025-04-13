package com.example.core.model.admin

data class MenuItems(
    val title: String,
    val url: String
)

val menuItems = listOf(
    MenuItems("Category", "https://res.cloudinary.com/dhl2sbjo5/image/upload/v1743531642/360_F_1033027173_zBOOOm47Vfg07gzhC4fdjhmAItou1ng3_hn8ttw.jpg"),
    MenuItems("Ingredient", "https://res.cloudinary.com/dhl2sbjo5/image/upload/v1743531642/Food-List-Ingredients-icon_kgd7vv.png"),
    MenuItems("Element", "https://res.cloudinary.com/dhl2sbjo5/image/upload/v1743531642/55120-200_vkryvq.png"),
    MenuItems("Products", "https://res.cloudinary.com/dhl2sbjo5/image/upload/v1743531642/4863042_pgcrm2.webp")

)
