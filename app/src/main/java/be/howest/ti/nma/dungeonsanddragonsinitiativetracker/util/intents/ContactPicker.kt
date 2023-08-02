package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.util.intents

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen.AddCampaignViewModel

@Composable
fun contactPicker(
    addCampaignViewModel: AddCampaignViewModel,
    context: Context
): ManagedActivityResultLauncher<Void?, Uri?> {
    val contactPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact(),
        onResult = { uri ->
            if (uri != null) {
                Log.d("ContactPicker", "Contact URI: $uri")
                val (name, email) = getContactInfoFromUri(context, uri)
                Log.d("ContactPicker", "Name: $name, Email: $email")
            }
        }
    )
    return contactPicker
}


fun selectContact(
    context: Context,
    contactPicker: ManagedActivityResultLauncher<Void?, Uri?>,
    launcherSinglePermission: ManagedActivityResultLauncher<String, Boolean>
) {
    val permission = android.Manifest.permission.READ_CONTACTS
    if (ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        contactPicker.launch(null)
    } else {
        launcherSinglePermission.launch(permission)
    }
}

@Composable
fun requestPermissionForContact(
    contactPicker: ManagedActivityResultLauncher<Void?, Uri?>,
    context: Context,
): ManagedActivityResultLauncher<String, Boolean> {
    val launcherSinglePermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            contactPicker.launch(null)
        } else {
            Toast.makeText(
                context,
                "Permission Denied",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    return launcherSinglePermission
}

//De twee onderstaande functies zijn volledig AI gegenereerd, geen idee hoe ze werken
@SuppressLint("Range")
fun getContactInfoFromUri(context: Context, contactUri: Uri): Pair<String?, String?> {
    var name: String? = null
    var email: String? = null

    // Define the fields you want to retrieve from the contact
    val projection: Array<String> = arrayOf(
        ContactsContract.Contacts._ID
    )

    // Perform the query using the contact URI and the defined projection
    context.contentResolver.query(
        contactUri,
        projection,
        null,
        null,
        null
    )?.use { cursor ->
        // Check if the cursor has any data
        if (cursor.moveToFirst()) {
            // Extract the contact ID from the cursor
            val contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            // Use the contact ID to obtain the email and name information
            getEmailAndNameFromContactId(context, contactId)?.let { (contactName, contactEmail) ->
                name = contactName
                email = contactEmail
            }
        }
    }

    // Log the retrieved information for debugging
    Log.d("ContactInfo", "Name: $name, Email: $email")

    return Pair(name, email)
}

private fun getEmailAndNameFromContactId(
    context: Context,
    contactId: Long
): Pair<String?, String?>? {
    var name: String? = null
    var email: String? = null

    // Define the fields you want to retrieve from the contact data table
    val projection: Array<String> = arrayOf(

        ContactsContract.CommonDataKinds.Email.DATA,
        ContactsContract.CommonDataKinds.Email.DISPLAY_NAME
    )

    // Specify the selection criteria to filter the data for the given contact ID
    val selection = ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?"

    // Specify the selection argument for the contact ID
    val selectionArgs = arrayOf(contactId.toString())

    // Perform the query using the contact data URI and the defined projection
    context.contentResolver.query(
        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        null
    )?.use { cursor ->
        // Check if the cursor has any data
        if (cursor.moveToFirst()) {
            // Extract the email and name from the cursor
            val emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
            val nameIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME)

            email = cursor.getString(emailIndex)
            name = cursor.getString(nameIndex)
        }
    }

    return if (name != null && email != null) Pair(name, email) else null
}

//Previous code:

//@Composable
//private fun SelectPlayerFromContactsButton() {
//    val context = LocalContext.current
//
//    val launcherSinglePermission = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//            selectContact(
//                context
//            )
//        } else {
//            Toast.makeText(
//                context,
//                "Permission Denied",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//
//    Button(
//        onClick = {
//            val permission = Manifest.permission.READ_CONTACTS
//
//            if (ContextCompat.checkSelfPermission(
//                    context,
//                    permission
//                ) == PackageManager
//                    .PERMISSION_GRANTED
//            ) {
//                selectContact(
//                    context
//                )
//            } else {
//                launcherSinglePermission.launch(permission)
//            }
//        },
//        modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))
//    ) {
//        Text(text = stringResource(id = R.string.select_player_from_contacts))
//    }
//}
//
//fun selectContact(
//    context: Context
//) {
//    val intent = Intent(Intent.ACTION_PICK).apply {
//        type = ContactsContract.CommonDataKinds.Email.CONTENT_TYPE
//    }
//        .putExtra(
//            ContactsContract.Intents.Insert.NAME,
//            true
//        )
//        .putExtra(
//            ContactsContract.Intents.Insert.EMAIL,
//            true
//        )
//
//    context.startActivity(intent)
//    intent.dataString?.let {
//        Log.d(
//            "implicitIntents",
//            it
//        )
//    }
//}