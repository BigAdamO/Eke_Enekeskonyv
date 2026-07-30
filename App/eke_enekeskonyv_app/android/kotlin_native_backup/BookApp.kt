package com.nagyadam.eke_enekeskonyv_app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nagyadam.eke_enekeskonyv_app.ui.HomeScreen
import com.nagyadam.eke_enekeskonyv_app.ui.PageScreen
import com.nagyadam.eke_enekeskonyv_app.ui.SearchResultScreen
import com.nagyadam.eke_enekeskonyv_app.ui.SearchScreen
import com.nagyadam.eke_enekeskonyv_app.ui.SettingScreen
import com.nagyadam.eke_enekeskonyv_app.ui.SongScreen
import com.nagyadam.eke_enekeskonyv_app.ui.TodaysSongScreen


@Composable
fun SongbookApp(
    navController: NavHostController = rememberNavController())
{
    NavHost(
        navController = navController,
        startDestination = "home_screen",
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            //.verticalScroll(rememberScrollState())
    ) {

        //HOME
        composable("home_screen") {
            HomeScreen(navController)
        }

        //ÉNEKEK
        composable(route = "song_screen/{idx}/{searched}",
            arguments = listOf(
                navArgument("idx") {type =
                    NavType.IntType
                },
                navArgument("searched") {type =
                    NavType.BoolType
               },
//                navArgument("songsIdx") {type =
//                    NavType.IntType}
            )
        ) { backStackEntry ->
            SongScreen(navController = navController,
                idx = backStackEntry.arguments?.getInt("idx")!!,
                searched = backStackEntry.arguments?.getBoolean("searched")!!
            )
        }

        // //LITURGIÁK
        // composable(route = "liturgy_screen/{idx}/{searched}",
        //     arguments = listOf(
        //         navArgument("idx") {type =
        //             NavType.IntType
        //         },
        //         navArgument("searched") {type =
        //             NavType.BoolType
        //        }
        //     )
        // ) { backStackEntry ->
        //     LiturgyScreen(navController = navController,
        //         idx = backStackEntry.arguments?.getInt("idx")!!,
        //         searched = backStackEntry.arguments?.getBoolean("searched")!!
        //     )
        // }

        // //14-ES LITURGIA
        // composable("liturgy14_screen/{idx}/{searched}",
        //     arguments = listOf(
        //         navArgument("idx") {type =
        //             NavType.IntType
        //         },
        //         navArgument("searched") {type =
        //             NavType.BoolType
        //         }
        //     )
        // ) { backStackEntry ->
        //     Liturgy14Screen(navController = navController,
        //         idx = backStackEntry.arguments?.getInt("idx")!!,
        //         searched = backStackEntry.arguments?.getBoolean("searched")!!
        //     )
        // }

        // //17-ES LITURGIA
        // composable("liturgy17_screen/{idx}/{searched}",
        //     arguments = listOf(
        //         navArgument("idx") {type =
        //             NavType.IntType
        //         },
        //         navArgument("searched") {type =
        //             NavType.BoolType
        //         }
        //     )
        // ) { backStackEntry ->
        //     Liturgy17Screen(navController = navController,
        //         idx = backStackEntry.arguments?.getInt("idx")!!,
        //         searched = backStackEntry.arguments?.getBoolean("searched")!!
        //     )
        // }

        // //ABC liturgiák
        // composable("liturgyabc_screen/{id}/{searched}",
        //     arguments = listOf(
        //         navArgument("id") {type =
        //             NavType.IntType
        //         },
        //         navArgument("searched") {type =
        //             NavType.BoolType
        //         }
        //     )
        // ) { backStackEntry ->
        //     AbcLiturgyScreen(navController = navController,
        //         id = backStackEntry.arguments?.getInt("id")!!,
        //         searched = backStackEntry.arguments?.getBoolean("searched")!!
        //     )
        // }



        //KERESÉS
        composable("search_screen/{searchForNumber}",
            arguments = listOf(
                navArgument("searchForNumber") {
                    type = NavType.BoolType
                }
            )
        ) {
            SearchScreen(navController = navController,
                searchForNumber = it.arguments?.getBoolean("searchForNumber")!!
            )
        }


        //KERESÉS RESULT + TARTALOM
        composable("searchResult_screen/{searched}",
            arguments = listOf(
                navArgument("searched") {
                    type = NavType.StringType
                }
            )
        ) {
            SearchResultScreen(
                searched = it.arguments?.getString("searched")!!,
                navController = navController
            )
        }

        //MAI ÉNEKEK
        composable("today") {
            TodaysSongScreen(navController)
        }

        //BEÁLLÍTÁSOK
        composable("settings") {
            SettingScreen(
                navController = navController
            )
        }


        //OLDALAS MEGOLDÁS
        composable("pages/{idList}",
            arguments = listOf(
                navArgument("idList") {
                    type = NavType.StringType
                }
            )
        ){
            val idListStr = it.arguments?.getString("idList")!!
            val idList = idListStr.split(",").filter { s -> s.isNotBlank() }.map { s -> s.toInt() }
            PageScreen(
                idList = idList
            )
        }
    }
}
