package com.nagyadam.eke_enekeskonyv_app.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nagyadam.eke_enekeskonyv_app.R
import com.nagyadam.eke_enekeskonyv_app.ui.theme.EkeEnekeskonyvTheme

var songNumbers = listOf<String>()
var i = 0

@Composable
fun TodaysSongScreen(navController: NavController) {
    EkeEnekeskonyvTheme {
        Scaffold(
            topBar = {
                SongTopBar(navController)
            }
        )
        { padding ->

            var songNumber by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_big))
                    .padding(padding)
                    .padding(top = 50.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NumberSearch(
                    value = songNumber,
                    onValueChanged = { songNumber = it },
                    navController = navController
                )

                Spacer(
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.padding_big))
                )

                //FELVESZ GOMB
                Button(
                    onClick = {
                        if (i < 5 && songNumber.isNotEmpty()) {
                            if (songNumber.toInt() in 1..591) {

                                songNumbers += songNumber
                                songNumber = ""
                                i++

                            } else {

                                songNumber = ""

                                Toast.makeText(
                                    navController.context,
                                    "Nincs ilyen számú ének!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(text = "Felvesz",
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontSize = (15 * textSize).sp
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_big)))


                    for (num in songNumbers) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .width(160.dp)
                                .height(50.dp)
                                .verticalScroll(rememberScrollState())
                                .background(color = colorResource(R.color.app_blue))
                                .border(
                                    shape = RoundedCornerShape(10.dp),
                                    width = 3.dp,
                                    color = colorResource(R.color.app_dark_blue)
                                )
                                .align(Alignment.CenterHorizontally)
                                .clickable {
                                    songNumbers -= num
                                    songNumber = "0"
                                    songNumber = ""
                                    i--
                                },
                            contentAlignment = Alignment.Center
                        )
                        {
                            Text(
                                text = num,
                                fontSize = (20 * textSize).sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))

                    }

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 64.dp * textSize),
                verticalArrangement = Arrangement.Bottom

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .width(60.dp)
                            .height(60.dp)
                            .align(Alignment.Bottom)
                            .background(colorResource(R.color.app_light_blue)),
                        contentAlignment = Alignment.Center,

                        ) {
                        //TÖRÖL GOMB
                        IconButton(
                            onClick = {
                                songNumbers = listOf()
                                i = 0
                                songNumber = "0"
                                songNumber = ""
                            },
                        ) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                tint = colorResource(R.color.app_dark_blue),
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Törlés"
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .width(60.dp)
                            .height(60.dp)
                            .background(colorResource(R.color.app_light_blue))
                            .align(Alignment.Bottom),
                        contentAlignment = Alignment.Center

                    ) {
                        //TOVÁBB/INDÍT GOMB
                        IconButton(
                            onClick = {
                                if (i > 0) {
                                    liturgyOrSong("0", navController, false)
                                    //-----------------------------------
                                    //val songs: List<Song>
//                                for (index in 0..<i) {
//                                    FillArray(index)
//                                }
                                    //-------------------------------
//                                var songs = listOf<Int>()
//                                for (num in songNumbers) {
//                                    songs += num.toInt()
//                                }
//                                navController.navigate("pages/${songs}")
                                }
                            }

                        ) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                tint = colorResource(R.color.app_dark_blue),
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Tovább"
                            )
                        }
                    }
                }
            }

        }
    }
}

fun liturgyOrSong(id: String, navController: NavController, searched: Boolean) {
    if (searched) {
        // if (id.toInt() in 1..13) {
        //     navController.navigate("liturgy_screen/${id}/${true}"){
        //         if (navController.previousBackStackEntry?.destination?.route == "search_screen/{searchForNumber}") {
        //             popUpTo("search_screen/${true}")
        //         }
        //         else {
        //             popUpTo("searchResult_screen/${"tartalom"}")
        //         }
        //     }
        // }
        // else if (id.toInt() in 14..16) {
        //     navController.navigate("liturgy14_screen/${id}/${true}"){
        //         if (navController.previousBackStackEntry?.destination?.route == "search_screen/{searchForNumber}") {
        //             popUpTo("search_screen/${true}")
        //         }
        //         else {
        //             popUpTo("searchResult_screen/${"tartalom"}")
        //         }
        //     }
        // }
        // else if (id.toInt() == 17) {
        //     navController.navigate("liturgy17_screen/${id}/${true}"){
        //         if (navController.previousBackStackEntry?.destination?.route == "search_screen/{searchForNumber}") {
        //             popUpTo("search_screen/${true}")
        //         }
        //         else {
        //             popUpTo("searchResult_screen/${"tartalom"}")
        //         }
        //     }
        // }
        // else if (id.toInt() in 1001..1010) {
        //     navController.navigate("liturgyabc_screen/${id}/${true}"){
        //         if (navController.previousBackStackEntry?.destination?.route == "search_screen/{searchForNumber}") {
        //             popUpTo("search_screen/${true}")
        //         }
        //         else {
        //             popUpTo("searchResult_screen/${"tartalom"}")
        //         }
        //     }
        // }
        // else 
        // {
            navController.navigate("song_screen/${id}/${true}"){
                if (navController.previousBackStackEntry?.destination?.route == "search_screen/{searchForNumber}") {
                    popUpTo("search_screen/${true}")
                }
                else {
                    popUpTo("searchResult_screen/${"tartalom"}")
                }
            }
        // }
    }
    else {
//         if (songNumbers[id.toInt()].toInt() in 1..13) {
//             navController.navigate("liturgy_screen/${id}/${false}"){
//                 popUpTo("today")
//             }
//         }
//         else if (songNumbers[id.toInt()].toInt() in 14..16) {
//             navController.navigate("liturgy14_screen/${id}/${false}"){
//                 popUpTo("today")
//             }
//         }
//         else if (songNumbers[id.toInt()].toInt() == 17) {
//             navController.navigate("liturgy17_screen/${id}/${false}"){
//                 popUpTo("today")
//             }
//         }
// //        else if (songNumbers[id.toInt()].toInt() in 1000..1200) {
// //            navController.navigate("liturgyabc_screen/${id}/${false}"){
// //                popUpTo("today")
// //            }
// //        }
        // else {
            navController.navigate("song_screen/${id}/${false}"){
                popUpTo("today")
            }
        // }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TodaysSongScreenPreview() {
    EvangelikusEnekeskonyvTheme(darkTheme = false) {
        TodaysSongScreen(rememberNavController())
    }
}
//
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun TodaysSongScreenPreviewDark() {
//    EvangelikusEnekeskonyvTheme(darkTheme = true) {
//        TodaysSongScreen(rememberNavController())
//    }
//}
