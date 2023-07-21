package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.subscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterUiState
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterViewModel

@Composable
fun PrimaryCharacters(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel,
    campaignId: Long,
) {
    val characterUiState by characterViewModel.characterUiState.collectAsState()
    val primaryCharacters by characterUiState.primaryCharacters.collectAsState(initial = emptyList())

    val selectedCharacters = characterUiState.selectedCharacters

    primaryCharacters.forEach { primaryCharacter ->
        CharacterCard(
            primaryCharacter,
            characterViewModel,
            characterUiState,
            isSelected = primaryCharacter in selectedCharacters
        ) { clickedCharacter ->
            if (selectedCharacters.contains(clickedCharacter)) {
                characterViewModel.deselectCharacter(clickedCharacter)
            } else {
                characterViewModel.selectCharacter(clickedCharacter)
            }
        }
    }
}

@Composable
fun CharacterCard(
    primaryCharacter: CampaignPlayerCharacterDetail,
    characterViewModel: CharacterViewModel,
    characterUiState: CharacterUiState,
    isSelected: Boolean,
    onCardClick: (CampaignPlayerCharacterDetail) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCardClick(primaryCharacter) },
        border = if (isSelected) BorderStroke(
            2.dp,
            Color.Blue
        ) else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row {
                Text(text = primaryCharacter.name)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "AC: ${primaryCharacter.armorClass}")
                Text(
                    text = "Initiative Modifier: ${
                        when {
                            primaryCharacter.initiativeModifier > 0 -> "+${primaryCharacter.initiativeModifier}"
                            primaryCharacter.initiativeModifier < 0 -> primaryCharacter.initiativeModifier.toString()
                            else -> "0"
                        }
                    }"
                )
                //Textfield for manuel initiative input
                BasicTextField(
                    value = if (primaryCharacter.initiative == null) {
                        ""
                    } else {
                        primaryCharacter.initiative.toString()
                    },
                    onValueChange = { newValue ->
                        characterViewModel
                            .updateInitiativeForPrimaryCharacter(
                                primaryCharacter,
                                newValue.toInt()
                            )
                    },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 16.sp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .width(60.dp)
                        .background(Color.LightGray)
                        .padding(8.dp)
                )

                // Button to roll for initiative
                Button(
                    onClick = {
                        val initiativeValue = characterViewModel.rollInitiative(
                            primaryCharacter
                                .initiativeModifier
                        )
                        characterViewModel.updateInitiativeForPrimaryCharacter(
                            primaryCharacter,
                            initiativeValue
                        )
                    },
                ) {
                    Text(text = "Roll Initiative")
                }
            }
        }
    }
}