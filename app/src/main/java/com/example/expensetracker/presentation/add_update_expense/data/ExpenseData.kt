package com.example.expensetracker.presentation.add_update_expense.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.DirectionsTransit
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MiscellaneousServices
import androidx.compose.material.icons.outlined.NightShelter
import androidx.compose.material.icons.outlined.Person4
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material.icons.outlined.TheaterComedy
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.expensetracker.common.toDate
import com.example.expensetracker.data.db.entities.Category
import com.example.expensetracker.data.db.entities.Expense

data class ExpenseData(
    val id: Int = 0,
    val title: String = "",
    val description: String? = null,
    val amount: String = "",
    val categoryId: Int = 0,
    val date: String? = null
) {
    companion object {
        val INVALID_EXPENSE_DATA = ExpenseData()

        fun ExpenseData.toExpenseEntity(isUpdate: Boolean): Expense {
            return Expense(
                id = id,
                title = title,
                description = description,
                amount = if(amount.isEmpty()) 0.0 else amount.toDouble(),
                categoryId = categoryId,
                date = if(isUpdate) date else System.currentTimeMillis().toDate()
            )
        }

        fun ExpenseData.getCategory() : String {
            return when(categoryId) {
                1 -> Category.HOUSING_CATEGORY.categoryName
                2 -> Category.TRANSPORTATION_CATEGORY.categoryName
                3 -> Category.FOOD_CATEGORY.categoryName
                4 -> Category.CLOTHING_CATEGORY.categoryName
                5 -> Category.PERSONAL_CARE_CATEGORY.categoryName
                6 -> Category.ENTERTAINMENT_CATEGORY.categoryName
                7 -> Category.GIFTS_CATEGORY.categoryName
                8 -> Category.CHARITY_CATEGORY.categoryName
                9 -> Category.MISCELLANEOUS_CATEGORY.categoryName
                else -> Category.HOUSING_CATEGORY.categoryName
            }
        }

        fun ExpenseData.getIcon() : ImageVector {
            return when(categoryId) {
                1 -> Icons.Outlined.Home
                2 -> Icons.Outlined.DirectionsTransit
                3 -> Icons.Outlined.Fastfood
                4 -> Icons.Outlined.Store
                5 -> Icons.Outlined.Person4
                6 -> Icons.Outlined.TheaterComedy
                7 -> Icons.Outlined.CardGiftcard
                8 -> Icons.Outlined.NightShelter
                9 -> Icons.Outlined.MiscellaneousServices
                else -> Icons.Outlined.Home
            }
        }
    }
}
