package com.kanishthika.moneymatters.display.reminder.data

import javax.inject.Inject

class MMReminderRepository @Inject constructor(
    private val mmReminderDao: MMReminderDao
) {
    val getAllMMReminder = mmReminderDao.getAllMMReminders()

    suspend fun insertMMReminder(mmReminder: MMReminder) {
        mmReminderDao.insertMMReminder(mmReminder)
    }

    suspend fun updateMMReminder(mmReminder: MMReminder) {
        mmReminderDao.updateMMReminder(mmReminder)
    }

    suspend fun deleteMMReminder(mmReminder: MMReminder) {
        mmReminderDao.deleteMMReminder(mmReminder)
    }

    suspend fun getMAXId(): Int {
        return mmReminderDao.getMaxId() ?: 0
    }

    suspend fun getReminderById(id: Int): MMReminder {
        return mmReminderDao.getReminderByID(id) ?: MMReminder(0, "", "", 0L)
    }

}