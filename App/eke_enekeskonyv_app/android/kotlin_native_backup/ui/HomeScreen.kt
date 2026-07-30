package com.nagyadam.eke_enekeskonyv_app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nagyadam.eke_enekeskonyv_app.R
import com.nagyadam.eke_enekeskonyv_app.loadSettingsFromFile
import com.nagyadam.eke_enekeskonyv_app.ui.theme.EkeEnekeskonyvTheme
import kotlinx.coroutines.delay

var textSize = 1f


@Composable
fun HomeScreen(navController: NavController) {
EkeEnekeskonyvTheme {

    Scaffold(
        topBar = {
            HomeTopBar()
        }
    )
    { padding ->

        val setting = loadSettingsFromFile(LocalContext.current)
        textSize = setting.fontSize
        var loading by remember { mutableStateOf(false) }
        val configuration = LocalConfiguration.current

        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .padding(padding)
                .padding(bottom = configuration.screenHeightDp.dp / 15)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Üdvözöljük az új Eke Énekeskönyv alkalmazásban!",
                    modifier = Modifier.padding(12.dp),
                    fontSize = (24 * textSize).sp,
                    lineHeight = (30 * textSize).sp,
                    textAlign = TextAlign.Center,
                )
            }

            Div()

            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {

            //KERESÉS
            Button(
                onClick = {
                    navController.navigate(route = "search_screen/${true}")
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) {
                Text(
                    text = "Keresés",
                    fontSize = (15 * textSize).sp
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_big)))

            //MAI ÉNEKEK
            Button(
                onClick = {
                    navController.navigate(
                        route = "today"
                    )

                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) {
                Text(
                    text = "Ének lista",
                    fontSize = (15 * textSize).sp
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_big)))


                //Tartalom
                Button(
                    onClick = {
//                        navController.navigate(route = "searchResult_screen/${"tartalom"}")
                        loading = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    )
                ) {
                    Text(
                        text = "Tartalom",
                        fontSize = (15 * textSize).sp
                    )
                }

                if (loading) {
                    LoadingScreen(navController)
                }
            }

            Div()

            //BEÁLLÍTÁSOK
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
//                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
//                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        navController.navigate(route = "settings")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    )
                ) {
                    Text(
                        text = "Beállítások",
                        fontSize = (15 * textSize).sp
                    )
                }
            }

        }

    }

} }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar() {
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                fontSize = (22).sp,
                lineHeight = (25).sp,

                )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.onSecondary,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun LoadingScreen(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(1500)
        isLoading = false
    }

    if (isLoading) {

        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )

    } else {
        navController.navigate(route = "searchResult_screen/${"tartalom"}")
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    EvangelikusEnekeskonyvTheme(darkTheme = false) {
        HomeScreen(rememberNavController())

   }
}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun HomeScreenPreviewDark() {
//    EvangelikusEnekeskonyvTheme(darkTheme = true) {
//        HomeScreen(rememberNavController())
//
//    }
//}

@Composable
fun Div() {
    Spacer(
        modifier = Modifier
            .height(10.dp * textSize)
    )

    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.tertiary
    )

    Spacer(
        modifier = Modifier
            .height(10.dp * textSize)
    )
}
