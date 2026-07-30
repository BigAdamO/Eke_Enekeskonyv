package com.nagyadam.eke_enekeskonyv_app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nagyadam.eke_enekeskonyv_app.R
import com.nagyadam.eke_enekeskonyv_app.ui.theme.BlueGrey80
import com.nagyadam.eke_enekeskonyv_app.ui.theme.EkeEnekeskonyvTheme
import com.nagyadam.eke_enekeskonyv_app.ui.theme.app_dark_blue


@Composable
fun SearchScreen(searchForNumber: Boolean,navController: NavController){
    EkeEnekeskonyvTheme {
        Scaffold(
            topBar = {
                SongTopBar(navController)
            }
        )
        { padding ->

            var searchedSong by remember { mutableStateOf("") }

            Column(modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_big))
                .padding(padding)
                .padding(top = 50.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if(searchForNumber){
                    NumberSearch(
                        value = searchedSong,
                        onValueChanged = { searchedSong = it },
                        navController = navController
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                    Text(
                        text = "Keresés cím alapján",
                        modifier = Modifier
                            .padding(15.dp)
                            .padding(top = 15.dp)
                            .clickable {
                                navController.navigate("search_screen/${false}")
                            },
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        fontSize = (15 * textSize).sp,
                        lineHeight = (20 * textSize).sp,
                        fontWeight = FontWeight.Medium,
                        )
                }
                else{
                    TitleSearch(
                        value = searchedSong,
                        onValueChanged = { searchedSong = it },
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_big)))

                //KERESÉS GOMB
                Button(
                    onClick = {
                        if(searchForNumber && searchedSong.toIntOrNull() != null && searchedSong.toInt() in 1..591) {
                            liturgyOrSong(searchedSong, navController, true)
                        }
                        else{
                            navController.navigate("searchResult_screen/${searchedSong}")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ){
                    Text(text = "Keresés",
                        fontSize = (15 * textSize).sp,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }

        }
    }
}



@Composable
fun NumberSearch(
    value: String,
    onValueChanged: (String) -> Unit,
    navController: NavController,
){

    TextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChanged,
        label = { Text(text = "Énekszám" ,
            fontSize = (18 * textSize).sp,
            lineHeight = (25 * textSize).sp,) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = BlueGrey80,
            unfocusedContainerColor = BlueGrey80,
            focusedTextColor = app_dark_blue,
            unfocusedTextColor = app_dark_blue,
            focusedIndicatorColor = app_dark_blue,
            unfocusedIndicatorColor = app_dark_blue,
            focusedLabelColor = app_dark_blue,
            unfocusedLabelColor = app_dark_blue,
            cursorColor = app_dark_blue,
        )
    )
}

@Composable
fun TitleSearch(
    value: String,
    onValueChanged: (String) -> Unit,
){
    TextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChanged,
        label = { Text(text = "Cím, első sor..." ,
            fontSize = (18 * textSize).sp,
            lineHeight = (25 * textSize).sp,) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = BlueGrey80,
            unfocusedContainerColor = BlueGrey80,
            focusedTextColor = app_dark_blue,
            unfocusedTextColor = app_dark_blue,
            focusedIndicatorColor = app_dark_blue,
            unfocusedIndicatorColor = app_dark_blue,
            focusedLabelColor = app_dark_blue,
            unfocusedLabelColor = app_dark_blue,
            cursorColor = app_dark_blue,
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    EkeEnekeskonyvTheme(darkTheme = false) {
        SearchScreen(true,rememberNavController())
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreviewDark() {
    EkeEnekeskonyvTheme(darkTheme = true) {
        SearchScreen(true,rememberNavController())
    }
}