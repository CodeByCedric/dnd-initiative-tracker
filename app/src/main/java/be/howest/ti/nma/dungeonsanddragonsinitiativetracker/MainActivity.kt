package be.howest.ti.nma.dungeonsanddragonsinitiativetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.DnDInitiativeTrackerNavHost
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.theme.DungeonsAndDragonsInitiativeTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DungeonsAndDragonsInitiativeTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DnDInitiativeTrackerApp()
                }
            }
        }
    }
}

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