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
    // Render the content for the Characters sub-screen
    Text(
        text = "Characters",
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}

//@Composable
//fun EnemiesScreen(
//    modifier: Modifier = Modifier,
//    characterViewModel: CharacterViewModel
//) {
//    val enemiesUiState = characterViewModel.enemiesUiState
//
//    when (enemiesUiState) {
//        is EnemiesUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
//        is EnemiesUiState.Success -> ResultOfEnemiesApiCall(
//            enemies = enemiesUiState.enemies,
//            modifier = modifier.fillMaxWidth()
//        )
//
//        is EnemiesUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
//    }
//}
//
//@Composable
//fun ResultOfEnemiesApiCall(
//    enemies: Response<EnemyResponse>,
//    modifier: Modifier
//) {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = modifier
//    ) {
//        if (enemies.isSuccessful) {
//            val enemyResponse = enemies.body()
//            val enemyList = enemyResponse?.results
//
//            if (!enemyList.isNullOrEmpty()) {
//                Column {
//                    enemyList.forEach { enemy ->
//                        Text(text = enemy.name)
//                    }
//                }
//            } else {
//                Text(text = "No enemies found")
//            }
//        } else {
//            Text(text = "Error: ${enemies.code()}")
//        }
//    }
//}
//
//@Composable
//fun LoadingScreen(modifier: Modifier = Modifier) {
//    Image(
//        modifier = modifier.size(200.dp),
//        painter = painterResource(R.drawable.loading_img),
//        contentDescription = stringResource(R.string.loading)
//    )
//}
//
//@Composable
//fun ErrorScreen(modifier: Modifier = Modifier) {
//    Column(
//        modifier = modifier,
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_connection_error),
//            contentDescription = ""
//        )
//        Text(
//            text = stringResource(R.string.loading_failed),
//            modifier = Modifier.padding(16.dp)
//        )
//    }
//}