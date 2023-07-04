package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui

import androidx.compose.runtime.Composable
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