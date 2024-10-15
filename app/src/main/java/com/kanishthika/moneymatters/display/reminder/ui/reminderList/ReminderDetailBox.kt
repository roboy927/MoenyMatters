package com.kanishthika.moneymatters.display.reminder.ui.reminderList

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kanishthika.moneymatters.config.utils.clickableOnce

@Composable
fun ReminderDetailBox(
    modifier: Modifier,
    title: String,
    description: String,
    date: String,
    time: String,
    icon: ImageVector,
    iconBackground: Color,
    editReminderNavigate: () -> Unit,
    cancelReminder: () -> Unit,
    highlighted: Boolean
) {
    var expanded by remember { mutableStateOf(highlighted) }

    Row(
        modifier = modifier
            .background(
                if (highlighted) {
                    MaterialTheme.colorScheme.secondaryContainer.copy(0.5f)
                } else {
                    Color.Transparent
                }
            )
            .animateContentSize()
            .fillMaxWidth()
            .clickable {
                expanded = !expanded
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .clip(shape = RoundedCornerShape(20))
                .background(iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "to be changed",
                modifier = modifier
                    .padding(6.dp)
                    .size(20.dp),
            )
        }
        Spacer(modifier = modifier.width(15.dp))
        Column(
            modifier = modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                maxLines = 1,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = modifier.height(2.dp))
            if (expanded) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer.copy(0.9f),
                    fontWeight = FontWeight.Light
                )
            }
            Spacer(modifier = modifier.height(2.dp))
            Text(
                text = "$date, $time",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                maxLines = 1,
                fontWeight = FontWeight.Normal
            )

        }
        Spacer(modifier = modifier.width(15.dp))
        Row {
            Icon(
                Icons.Default.Create,
                contentDescription = "Edit",
                modifier = modifier.clickableOnce {
                    editReminderNavigate()
                }
            )
            Spacer(modifier = modifier.width(15.dp))
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete",
                modifier = modifier.clickableOnce {
                    cancelReminder()
                }
            )
        }
    }
}
