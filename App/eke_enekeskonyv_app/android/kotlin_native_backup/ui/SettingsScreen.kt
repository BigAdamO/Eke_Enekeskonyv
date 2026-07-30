package com.nagyadam.eke_enekeskonyv_app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nagyadam.eke_enekeskonyv_app.R
import com.nagyadam.eke_enekeskonyv_app.Settings
import com.nagyadam.eke_enekeskonyv_app.darkMode
import com.nagyadam.eke_enekeskonyv_app.loadSettingsFromFile
import com.nagyadam.eke_enekeskonyv_app.saveSettingsToFile
import com.nagyadam.eke_enekeskonyv_app.ui.theme.EkeEnekeskonyvTheme


@Composable
fun SettingScreen(navController: NavController){
EkeEnekeskonyvTheme {

    Scaffold(
        topBar = {
            SongTopBar(navController)
        }
    )
    { padding ->

        val context = LocalContext.current
        val checked = darkMode
        var sliderPosition by remember { mutableFloatStateOf(textSize) }

        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),

            ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            
            Title("Beállítások")

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            //Sötét téma
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Sötét téma: ",
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                    fontSize = (20 * textSize).sp
                )

                Switch(
                    checked = checked,
                    onCheckedChange = {
                        val file = loadSettingsFromFile(context)
                        darkMode = !darkMode

                        saveSettingsToFile(context, Settings(darkMode, file.fontSize))

                        navController.navigate("settings"){
                            popUpTo("home_screen")
                        }
                    },
                    colors = SwitchColors(
                        checkedThumbColor = MaterialTheme.colorScheme.secondary,
                        checkedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                        checkedBorderColor = MaterialTheme.colorScheme.secondary,
                        checkedIconColor = MaterialTheme.colorScheme.secondary,
                        //-------------------------------
                        uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                        uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                        uncheckedBorderColor = MaterialTheme.colorScheme.secondary,
                        uncheckedIconColor = MaterialTheme.colorScheme.secondary,
                        //-----------------------------
                        disabledCheckedThumbColor = MaterialTheme.colorScheme.secondary,
                        disabledCheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                        disabledCheckedBorderColor = MaterialTheme.colorScheme.secondary,
                        disabledCheckedIconColor = MaterialTheme.colorScheme.secondary,
                        //-----------------------------
                        disabledUncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                        disabledUncheckedTrackColor = MaterialTheme.colorScheme.secondary,
                        disabledUncheckedBorderColor = MaterialTheme.colorScheme.secondary,
                        disabledUncheckedIconColor = MaterialTheme.colorScheme.secondary
                    )
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Betüméret: ",
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                    fontSize = (20 * textSize).sp
                )
            }
            //Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            Slider(
                modifier = Modifier
                    .padding(10.dp)
                ,
                value = sliderPosition,
                onValueChange = {
                    textSize = it
                    sliderPosition = it

                    val file = loadSettingsFromFile(context)
                    saveSettingsToFile(context, Settings(file.darkMode, textSize))
                },
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.secondary,
                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                    activeTickColor = MaterialTheme.colorScheme.secondaryContainer,
                    inactiveTickColor = MaterialTheme.colorScheme.secondary
                ),
                steps = 5,
                valueRange = 0.5f..2f
            )

        }
    }
}
}

@Preview
@Composable
fun SettingScreenPreview(
    navController: NavController = rememberNavController()
){
    SettingScreen(navController)
}