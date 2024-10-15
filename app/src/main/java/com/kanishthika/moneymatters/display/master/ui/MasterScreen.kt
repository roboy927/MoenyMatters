package com.kanishthika.moneymatters.display.master.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.config.mmComposable.MMTopAppBar
import com.kanishthika.moneymatters.config.utils.clickableOnce
import com.kanishthika.moneymatters.display.master.data.masterMenuItemList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterScreen(
    modifier: Modifier,
    navController: NavController
) {
    val menuItemList = masterMenuItemList()

    Log.d("TAG", "MasterScreen: Started ")

    BackHandler {
        navController.navigateUp()
    }

    Scaffold(
        modifier = modifier,
        topBar = { MMTopAppBar(titleText = "Master") },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(12.dp)
                .fillMaxWidth(

                )
        ) {

            LazyColumn {
                itemsIndexed(menuItemList) { _, item ->
                    MasterMenuItem(
                        modifier = modifier,
                        itemName = item.name,
                        onItemClick = {
                            navController.navigate(item.route)
                        },
                        itemIcon = item.icon
                    )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                    )
                }

            }
        }

    }
}

@Composable
fun MasterMenuItem(
    modifier: Modifier,
    itemName: String,
    itemIcon: ImageVector,
    onItemClick: () -> Unit
) {
    Box(modifier = modifier
        .clickableOnce { onItemClick() }
        .fillMaxWidth()
        .padding(vertical = 16.dp, horizontal = 8.dp))
    {
        Row (
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Row {
                Icon(
                    modifier = modifier.size(20.dp),
                    imageVector = itemIcon,
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = itemName)
                Spacer(modifier = modifier.width(15.dp))
                Text(
                    text = itemName,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Icon(
                Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = "Go",
                tint = MaterialTheme.colorScheme.onBackground.copy(0.5f)
            )
        }

    }
}

