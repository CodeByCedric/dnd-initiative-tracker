package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterViewModel

@Composable
fun EnemiesScreen(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel
) {
    val enemiesUiState = characterViewModel.enemiesUiState
    // Render the content for the Enemies sub-screen
    Text(
        text = enemiesUiState,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}