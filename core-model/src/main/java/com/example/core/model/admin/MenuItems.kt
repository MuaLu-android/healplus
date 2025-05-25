package com.example.core.model.admin

data class MenuItems(
    val title: String,
    val url: String
)

val menuItems = listOf(
    MenuItems("Danh mục chính", "https://res.cloudinary.com/dhl2sbjo5/image/upload/v1743531642/360_F_1033027173_zBOOOm47Vfg07gzhC4fdjhmAItou1ng3_hn8ttw.jpg"),
    MenuItems("Danh mục phụ", "https://res.cloudinary.com/dhl2sbjo5/image/upload/v1743531642/Food-List-Ingredients-icon_kgd7vv.png"),
    MenuItems("Danh mục chi tiết", "https://res.cloudinary.com/dhl2sbjo5/image/upload/v1743531642/55120-200_vkryvq.png"),
    MenuItems("Sản phẩm", "https://res.cloudinary.com/dhl2sbjo5/image/upload/v1743531642/4863042_pgcrm2.webp")

)
