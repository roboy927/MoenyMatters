package com.kanishthika.moneymatters.display.reminder.ui.reminderList


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.mmComposable.MMEmptyStateScreen
import com.kanishthika.moneymatters.config.mmComposable.MMLoadingScreen
import com.kanishthika.moneymatters.config.mmComposable.MMTopAppBar
import com.kanishthika.moneymatters.config.reminder.ReminderAlarmManager
import com.kanishthika.moneymatters.display.reminder.data.MMReminder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderListScreen(
    modifier: Modifier,
    reminderId: String?,
    editReminderNavigate: (MMReminder) -> Unit,
    navigateToAdd: () -> Unit,
    reminderListViewModel: ReminderListViewModel = hiltViewModel()
) {
    val reminderListUiState by reminderListViewModel.reminderListUiState.collectAsState()

    val context = LocalContext.current
    val reminderAlarmManager = ReminderAlarmManager(context = context)

    Scaffold(
        topBar = { MMTopAppBar(titleText = "Reminders") },
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.height(IntrinsicSize.Min),
                onClick = { navigateToAdd() },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(0.7f),
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer.copy(0.8f)
            ) {
                Row(
                    modifier = modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Filled.AddCircle, contentDescription = "Add")
                    Text(
                        text = "Reminder",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (reminderListUiState) {
                ReminderListUiState.Empty -> MMEmptyStateScreen(modifier = modifier)
                ReminderListUiState.Loading -> MMLoadingScreen(modifier = modifier)
                is ReminderListUiState.Success -> {
                    val todayReminderList =
                        (reminderListUiState as ReminderListUiState.Success).todayReminder
                    val tomorrowReminderList =
                        (reminderListUiState as ReminderListUiState.Success).tomorrowReminder
                    val weekReminderList =
                        (reminderListUiState as ReminderListUiState.Success).weekReminder
                    val monthReminderList =
                        (reminderListUiState as ReminderListUiState.Success).monthReminder
                    val otherReminderList =
                        (reminderListUiState as ReminderListUiState.Success).otherReminder
                    val oldReminderList =
                        (reminderListUiState as ReminderListUiState.Success).oldReminder

                    LazyColumn(
                        modifier = modifier.animateContentSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (todayReminderList.isNotEmpty()) {
                            item {
                                ReminderLists(
                                    listHeading = "Today's Reminders",
                                    list = todayReminderList,
                                    editReminderNavigate = { editReminderNavigate(it) },
                                    cancelReminder = { reminder ->
                                        reminderListViewModel.cancelReminder(reminder)
                                        reminderAlarmManager.cancelAlarm(reminderId = reminder.id)
                                    },
                                    reminderId = reminderId
                                )
                            }
                        }
                        if (tomorrowReminderList.isNotEmpty()) {
                            item {
                                ReminderLists(
                                    listHeading = "Tomorrow's Reminders",
                                    list = tomorrowReminderList,
                                    editReminderNavigate = { editReminderNavigate(it) },
                                    cancelReminder = { reminder ->
                                        reminderListViewModel.cancelReminder(reminder)
                                        reminderAlarmManager.cancelAlarm(reminderId = reminder.id)
                                    },
                                    reminderId = reminderId
                                )
                            }
                        }
                        if (weekReminderList.isNotEmpty()) {
                            item {
                                ReminderLists(
                                    listHeading = "This Week Reminders",
                                    list = weekReminderList,
                                    editReminderNavigate = { editReminderNavigate(it) },
                                    cancelReminder = { reminder ->
                                        reminderListViewModel.cancelReminder(reminder)
                                        reminderAlarmManager.cancelAlarm(reminderId = reminder.id)
                                    },
                                    reminderId = reminderId
                                )
                            }
                        }
                        if (monthReminderList.isNotEmpty()) {
                            item {
                                ReminderLists(
                                    listHeading = "This Month Reminders",
                                    list = monthReminderList,
                                    editReminderNavigate = { editReminderNavigate(it) },
                                    cancelReminder = { reminder ->
                                        reminderListViewModel.cancelReminder(reminder)
                                        reminderAlarmManager.cancelAlarm(reminderId = reminder.id)
                                    },
                                    reminderId = reminderId
                                )
                            }
                        }
                        if (otherReminderList.isNotEmpty()) {
                            item {
                                ReminderLists(
                                    listHeading = "Future Reminders",
                                    list = otherReminderList,
                                    editReminderNavigate = { editReminderNavigate(it) },
                                    cancelReminder = { reminder ->
                                        reminderListViewModel.cancelReminder(reminder)
                                        reminderAlarmManager.cancelAlarm(reminderId = reminder.id)
                                    },
                                    reminderId = reminderId
                                )
                            }
                        }
                        if (oldReminderList.isNotEmpty()) {
                            item {
                                Column(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.uni_corner_radius)))
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .padding(8.dp)
                                        .animateContentSize()
                                ) {
                                    Row(
                                        modifier = modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                reminderListViewModel.changeOpenOldTransaction()
                                            }
                                            .padding(8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Old Reminders",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                        Icon(
                                            Icons.Filled.ArrowDropDown,
                                            contentDescription = "Open",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    if ((reminderListUiState as ReminderListUiState.Success).openOldTransaction) {
                                        ReminderLists(
                                            list = oldReminderList,
                                            editReminderNavigate = { editReminderNavigate(it) },
                                            cancelReminder = { reminder ->
                                                reminderListViewModel.cancelReminder(reminder)
                                                reminderAlarmManager.cancelAlarm(reminderId = reminder.id)
                                            },
                                            reminderId = reminderId
                                        )

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



