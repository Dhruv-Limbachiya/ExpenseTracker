package com.example.expensetracker.data.db.entities

enum class Category(val categoryId: Int,val categoryName: String) {
    HOUSING_CATEGORY(1, "Household"),
    TRANSPORTATION_CATEGORY(2,"Transportation"),
    FOOD_CATEGORY(3,"Food & Beverages"),
    CLOTHING_CATEGORY(4,"Clothing"),
    PERSONAL_CARE_CATEGORY(5,"Personal Care"),
    ENTERTAINMENT_CATEGORY(6,"Entertainment"),
    GIFTS_CATEGORY(7,"Gifts"),
    CHARITY_CATEGORY(8,"Charity"),
    MISCELLANEOUS_CATEGORY(9,"Miscellaneous"),
}

