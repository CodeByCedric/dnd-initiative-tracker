package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.navigationTesting

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerApp
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen.AddCampaignScreenDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen.CampaignScreenDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterScreenDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.encounterBuilderScreen.EncounterBuilderScreenDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.initiativeScreen.InitiativeScreenDestination
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.skirmishScreen.SkirmishScreenDestination
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DungeonsAndDragonsScreenNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupDnDNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            DnDInitiativeTrackerApp(navController = navController)
        }
    }

    @Test
    fun dndNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(CampaignScreenDestination.route)
    }

    @Test
    fun dndNavHost_verifyBackNavigationNotShownOnCampaignScreen() {
        val backText =
            composeTestRule.activity.getString(
                be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R.string.back_button
            )
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun dndNavHost_clickOnCampaign_navigatesToCharacterOverviewScreen() {
        composeTestRule.onAllNodesWithTag("campaignCard")[0].performClick()
        composeTestRule.onNodeWithText("To the Character Overview Screen!").performClick()
        navController.assertCurrentRouteName(
            CharacterScreenDestination.route
        )
    }

    @Test
    fun dndNavHost_clickOnToEncounterBuilder_navigatesToEncounterBuilder() {
        navigateToEncounterBuilderScreen()
        navController.assertCurrentRouteName(EncounterBuilderScreenDestination.route)
    }

    @Test
    fun dndNavHost_clickToInitiativeScreen_navigatesToInitiativeScreen() {
        navigateToInitiativeScreen()
        navController.assertCurrentRouteName(InitiativeScreenDestination.route)
    }

    @Test
    fun dndNavHost_clickToSkirmishScreen_navigatesToSkirmishScreen() {
        navigateToSkirmishScreen()
        navController.assertCurrentRouteName(SkirmishScreenDestination.route)
    }

    @Test
    fun dndNavHost_clickBackOnCharacterOverviewScreen_navigatesToCampaignScreen() {
        navigateToCharacterOverviewScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CampaignScreenDestination.route)

    }

    @Test
    fun dndNavHost_clickBackOnEncounterBuilderScreen_navigatesToCharacterOverviewScreen() {
        navigateToEncounterBuilderScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CharacterScreenDestination.route)
    }

    @Test
    fun dndNavHost_clickBackOnInitiativeScreen_navigatesToEncounterBuilderScreen() {
        navigateToInitiativeScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(EncounterBuilderScreenDestination.route)
    }

    @Test
    fun dndNavHost_clickBackOnSkirmishScreen_navigatesToInitiativeScreen() {
        navigateToSkirmishScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(InitiativeScreenDestination.route)
    }

    @Test
    fun dndNavHost_clickAddNewCampaign_navigatesToAddNewCampaignScreen() {
        navigateToAddNewCampaignScreen()
        navController.assertCurrentRouteName(AddCampaignScreenDestination.route)
    }

    private fun navigateToCharacterOverviewScreen() {
        composeTestRule.onAllNodesWithTag("campaignCard")[0].performClick()
        composeTestRule.onNodeWithText("To the Character Overview Screen!").performClick()
    }

    private fun navigateToEncounterBuilderScreen() {
        navigateToCharacterOverviewScreen()
        composeTestRule.onNodeWithStringId(
            be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R.string
                .to_encounter_builder_screen_button
        ).performClick()
    }

    private fun navigateToInitiativeScreen() {
        navigateToEncounterBuilderScreen()
        composeTestRule.onNodeWithStringId(
            be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R.string
                .to_initiative_screen_button
        ).performClick()
    }

    private fun navigateToSkirmishScreen() {
        navigateToInitiativeScreen()
        composeTestRule.onNodeWithStringId(
            be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R.string
                .to_skirmish_screen_button
        ).performClick()
    }

    private fun navigateToAddNewCampaignScreen() {
        composeTestRule.onNodeWithTag("addCampaignFOB").performClick()
    }

    private fun performNavigateUp() {
        val backText =
            composeTestRule.activity.getString(be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }
}