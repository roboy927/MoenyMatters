package com.kanishthika.moneymatters.display.accounting.type.borrower.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.kanishthika.moneymatters.config.mmComposable.HorizontalLineWithCenteredText
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.type.borrower.data.Borrower
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.AddOrUpdateItemScreen
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.InformationText
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.MMOutlinedTextFieldWithState

@SuppressLint("Range")
@Composable
fun AddBorrowerScreen(
    modifier: Modifier,
    borrowerModel: BorrowerModel,
    borrower: Borrower? = null,
    navController: NavController
) {
    val borrowerUiState by borrowerModel.uiState.collectAsState()
    var borrowerEditEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (borrower != null) {
            borrowerModel.updateName(borrower.name)
            borrowerModel.updateDescription(borrower.description)
            borrowerModel.updateAmount(borrower.amount.toString())
            borrowerModel.isEditEnabled(capitalizeWords(borrower.name)) {
                borrowerEditEnabled = it
            }
        }
    }

    val context = LocalContext.current
    val contactLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact()
    ) { contactUri ->
        contactUri?.let {
            borrowerModel.updateName(getContactName(context.contentResolver, contactUri))
            getContactPhoneNumber(context.contentResolver, contactUri)?.let { string ->
                borrowerModel.updateDescription(
                    string
                )
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("TAG", "AddBorrowerScreen: isGranted")
            contactLauncher.launch(null)
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    AddOrUpdateItemScreen(
        viewModel = borrowerModel,
        modifier = modifier,
        navController = navController,
        screenTitle = "Add Borrower",
        buttonText = if (borrower == null) "Add" else "Update",
        isEnabled = borrowerModel.isAnyFieldIsEmpty(borrowerUiState).not(),
        onBottomBarClick = {
            if (borrower != null) {
                borrowerModel.updateItem(borrower.copy(
                    borrowerName = borrowerUiState.name,
                    borrowerContactNumber = borrowerUiState.description,
                    amount = borrowerUiState.amount.toDouble()
                )) {
                    navController.popBackStack()
                }
            } else {
                borrowerModel.addItemToDB {
                    navController.popBackStack()
                }
            }
        }
    ) {
        MMOutlinedTextFieldWithState(
            enabled = borrowerEditEnabled,
            value = borrowerUiState.name,
            onValueChange = { borrowerModel.updateName(it) },
            labelText = "Name",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )
        MMOutlinedTextFieldWithState(
            value = borrowerUiState.description,
            onValueChange = { borrowerModel.updateDescription(it) },
            labelText = "Amount",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
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
                    when (PackageManager.PERMISSION_GRANTED) {
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_CONTACTS
                        ) -> {
                            // Some works that require permission
                            contactLauncher.launch(null)
                        }

                        else -> {
                            // Asking for permission
                            permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                        }
                    }
                },
                text = "Select from Contact",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium,
                textDecoration = TextDecoration.Underline,
                fontStyle = FontStyle.Italic
            )
        }
        MMOutlinedTextFieldWithState(
            enabled = borrowerEditEnabled,
            value = borrowerUiState.amount,
            onValueChange = { borrowerModel.updateAmount(it) },
            labelText = "Amount",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            )
        )
        if (!borrowerEditEnabled) {
            InformationText(modifier = modifier)
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

private fun getContactPhoneNumber(contentResolver: ContentResolver, contactUri: Uri): String? {
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
