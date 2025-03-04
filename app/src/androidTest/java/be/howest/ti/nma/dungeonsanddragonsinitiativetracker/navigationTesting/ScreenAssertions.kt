package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.navigationTesting

import androidx.navigation.NavController
import org.junit.Assert

fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    Assert.assertEquals(
        expectedRouteName,
        currentBackStackEntry?.destination?.route
    )
}