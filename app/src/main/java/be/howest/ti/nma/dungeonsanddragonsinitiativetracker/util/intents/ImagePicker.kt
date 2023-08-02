package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.util.intents

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen.AddCampaignViewModel


@Composable
fun photoPicker(
    addCampaignViewModel: AddCampaignViewModel,
    context: Context
): ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?> {
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                addCampaignViewModel.updateCampaignImage(uri)
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    flag
                )
            }
        }
    )
    return photoPicker
}

fun selectImage(
    context: Context,
    photoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    launcherSinglePermission: ManagedActivityResultLauncher<String, Boolean>
) {
    val permission = Manifest.permission.READ_MEDIA_IMAGES
    if (ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    } else {
        launcherSinglePermission.launch(permission)
    }
}

@Composable
fun requestPermissionForVisualMedia(
    photoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    context: Context
): ManagedActivityResultLauncher<String, Boolean> {
    val launcherSinglePermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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