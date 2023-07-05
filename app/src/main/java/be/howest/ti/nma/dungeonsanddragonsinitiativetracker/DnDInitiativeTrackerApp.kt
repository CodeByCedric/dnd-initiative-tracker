package be.howest.ti.nma.dungeonsanddragonsinitiativetracker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.DnDInitiativeTrackerNavHost
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.theme.DungeonsAndDragonsInitiativeTrackerTheme

@Composable
fun DnDInitiativeTrackerApp(
    navController: NavHostController = rememberNavController()
) {
    DnDInitiativeTrackerNavHost(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun InitiativeTrackerAppPreview() {
    DungeonsAndDragonsInitiativeTrackerTheme {
        DnDInitiativeTrackerApp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DnDInitiativeTrackerTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}