package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.createCharacterScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.AppViewModelProvider
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.navigation.NavigationDestination

object CreateCharacterScreenDestination : NavigationDestination {
    override val route: String = "create_primary_character_screen"
    override val titleRes: Int = R.string.create_primary_character_screen
}

@Composable
fun CreateCharacterScreen(
    campaignId: Long,
    navigateToCharacterScreen: () -> Unit,
    navigateBack: () -> Boolean,
    onNavigateUp: () -> Boolean,
    canNavigateBack: Boolean = true,
    createCharacterViewModel: CreateCharacterViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    Text(text = "CreateCharacterScreen, campaignID:$campaignId")
}
