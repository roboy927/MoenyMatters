package com.kanishthika.moneymatters.display.accounting.type.lb.borrower.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kanishthika.moneymatters.config.components.HorizontalLineWithCenteredText
import com.kanishthika.moneymatters.config.components.MMColumnScaffoldContentColumn
import com.kanishthika.moneymatters.config.components.MMOutlinedTextField
import com.kanishthika.moneymatters.config.components.MMTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("Range")
@Composable
fun AddBorrowerScreen(
    modifier: Modifier,
    borrowerModel: BorrowerModel
) {
    val borrowerUiState by borrowerModel.addBorrowerUiState.collectAsState()

    val context = LocalContext.current
    val activity = context as Activity
    val contactLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact()
    ) { contactUri ->
        contactUri?.let {
            borrowerModel.updateBorrowerName(getContactName(context.contentResolver, contactUri))
            getContactPhoneNumber(context.contentResolver, contactUri)?.let { string ->
                borrowerModel.updateBorrowerContactNumber(
                    string
                )
            }
        }
    }

    Scaffold(
        modifier = modifier.imePadding(),
        topBar = { MMTopAppBar(titleText = "Add Borrower") },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                modifier = modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .clickable {
                        if (borrowerModel.isAnyFieldIsEmpty(borrowerUiState)){
                          Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        } else {
                            borrowerModel.addBorrower()
                        }
                    }
            ) {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Add Borrower",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }
    ) { paddingValue ->
        MMColumnScaffoldContentColumn(modifier = modifier, scaffoldPaddingValues = paddingValue) {

            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = borrowerUiState.borrowerName,
                onValueChange = { input ->
                    borrowerModel.updateBorrowerName(input)
                },
                labelText = "Borrower Name",
                supportingText = { Text(text = "Enter a new Borrower name or directly select from contacts below ") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Next
                )
            )
            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = borrowerUiState.borrowerContactNumber,
                onValueChange = { input ->
                    borrowerModel.updateBorrowerContactNumber(input)
                },
                labelText = "Borrower Contact",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Next
                )
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalLineWithCenteredText(modifier, "or")
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = modifier.clickable {
                        if (hasContactPermission(context)) {
                            contactLauncher.launch(null)
                        } else {
                            requestContactPermission(context, activity)
                        }
                    },
                    text = "Select from Contact",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = TextDecoration.Underline,
                    fontStyle = FontStyle.Italic
                )
            }

            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = borrowerUiState.amount,
                onValueChange = { input ->
                    borrowerModel.updateAmount(input)
                },
                labelText = "Initial Amount",
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Next
                )
            )
        }
    }
}


private fun getContactName(contentResolver: ContentResolver, contactUri: Uri): String {
    var contactName = ""
    val cursor = contentResolver.query(contactUri, null, null, null, null)
    cursor?.use { c ->
        if (c.moveToFirst()) {
            contactName =
                c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
        }
    }
    return contactName
}

fun getContactPhoneNumber(contentResolver: ContentResolver, contactUri: Uri): String? {
    var phoneNumber: String? = null
    val cursor: Cursor? = contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
        arrayOf(contactUri.lastPathSegment),
        null
    )
    cursor?.use {
        if (it.moveToFirst()) {
            phoneNumber =
                it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
        }
    }
    return phoneNumber
}


fun hasContactPermission(context: Context): Boolean {
    // on below line checking if permission is present or not.
    return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) ==
            PackageManager.PERMISSION_GRANTED
}

fun requestContactPermission(context: Context, activity: Activity) {
    // on below line if permission is not granted requesting permissions.
    if (!hasContactPermission(context)) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.READ_CONTACTS),
            1
        )
    }
}