package com.kanishthika.moneymatters.display.dashboard.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.navigation.NavigationItem
import com.kanishthika.moneymatters.config.navigation.Screen


data class ClickableItem(val icon: ImageVector, val label: String, val route: String)

@Composable
fun dashBoardMenuItems(): List<ClickableItem> {
    return listOf(
        ClickableItem(
            ImageVector.vectorResource(id = R.drawable.lender),
            "TTS",
            NavigationItem.TransferToSelfScreen.route
        ),
        ClickableItem(
            ImageVector.vectorResource(id = R.drawable.investment),
            "Investment",
            NavigationItem.AddInsurance.createAddInsuranceScreen(null)
        ),
        ClickableItem(
            ImageVector.vectorResource(id = R.drawable.loan),
            "Accounting",
            Screen.ACCOUNTING.name
        ),
        ClickableItem(
            Icons.Default.AccountCircle,
            "Reminders",
            NavigationItem.ReminderListScreen.route
        ),
        ClickableItem(
            Icons.Default.MailOutline,
            "Transaction",
            NavigationItem.TransactionList.route
        ),
        ClickableItem(Icons.Default.Menu, "Master", NavigationItem.Master.route),
        ClickableItem(
            Icons.Default.Notifications,
            "Reminder",
            NavigationItem.AddReminderScreen.createAddReminderScreen(null)
        ),
        ClickableItem(Icons.Default.Menu, "Label Type", NavigationItem.LabelTypeScreen.route),
        ClickableItem(Icons.Default.ShoppingCart, "Labels", NavigationItem.LabelListScreen.route)
    )
}

@Composable
fun dashBoardBottomBarItems(): List<ClickableItem> {
    return listOf(
        ClickableItem(
            Icons.Default.MailOutline,
            "History",
            NavigationItem.TransactionList.route
        ),
        ClickableItem(
            Icons.Default.Notifications,
            "Reminder",
            NavigationItem.AddReminderScreen.createAddReminderScreen(null)
        ),
        ClickableItem(
            Icons.Default.AddCircle,
            "Transaction",
            NavigationItem.AddTransaction2.createAddTransactionScreen(
                null
            )
        ),
        ClickableItem(
            Icons.Default.AccountCircle,
            "To Self",
            NavigationItem.TransferToSelfScreen.route
        )
    )
}