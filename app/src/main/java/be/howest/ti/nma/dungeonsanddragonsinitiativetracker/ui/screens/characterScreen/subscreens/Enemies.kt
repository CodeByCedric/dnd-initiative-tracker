package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EnemiesScreen(
    modifier: Modifier = Modifier
) {
    // Render the content for the Enemies sub-screen
    Text(
        text = "Enemies",
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}