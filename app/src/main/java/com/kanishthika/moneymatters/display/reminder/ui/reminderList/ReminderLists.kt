package com.kanishthika.moneymatters.display.reminder.ui.reminderList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.utils.separateDateFromTimeStamp
import com.kanishthika.moneymatters.config.utils.separateTimeFromTimeStamp
import com.kanishthika.moneymatters.display.reminder.data.MMReminder

@Composable
fun ReminderLists(
    modifier: Modifier = Modifier,
    listHeading: String? = null,
    list: List<MMReminder>,
    editReminderNavigate: (MMReminder) -> Unit,
    cancelReminder: (MMReminder) -> Unit,
    reminderId: String?
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.uni_corner_radius)))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp)
    ) {
        if (listHeading != null) {
            Box(
                modifier = modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = listHeading,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = modifier.height(4.dp))
        list.forEach { reminder ->
            ReminderDetailBox(
                modifier = modifier,
                title = reminder.title,
                description = reminder.description,
                date = separateDateFromTimeStamp(reminder.timeStamp),
                time = separateTimeFromTimeStamp(reminder.timeStamp),
                icon = Icons.Default.Notifications,
                iconBackground = MaterialTheme.colorScheme.tertiary,
                editReminderNavigate = { editReminderNavigate(reminder) },
                cancelReminder = {
                    cancelReminder(reminder)
                },
                highlighted = reminderId != null && reminderId == reminder.id.toString()
            )

            if (list.indexOf(reminder) != list.lastIndex) {
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