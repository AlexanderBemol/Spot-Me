package montanez.alexander.spotme.ui.views.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import montanez.alexander.spotme.ui.views.home.BottomNavItem
import montanez.alexander.spotme.ui.views.home.HomeContent
import montanez.alexander.spotme.ui.views.home.TopArtistsContent
import montanez.alexander.spotme.ui.views.home.TopSongsContent

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeNavGraph(paddingValues: PaddingValues,homeNavController: NavHostController){

    val lastRoute = homeNavController.previousBackStackEntry?.destination?.route.toString()
    val animationDuration = 500

    AnimatedNavHost(navController = homeNavController, startDestination = BottomNavItem.Home.route ){
        composable(
            BottomNavItem.Home.route,
            exitTransition = { slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Up,
                animationSpec = tween(animationDuration)
            )},
            enterTransition = {
                val slideAnimation =
                    when(lastRoute){
                        BottomNavItem.TopSongs.route ->  AnimatedContentScope.SlideDirection.Left
                        BottomNavItem.TopArtists.route -> AnimatedContentScope.SlideDirection.Right
                        else -> AnimatedContentScope.SlideDirection.Up
                    }
                slideIntoContainer(
                    slideAnimation,
                    animationSpec = tween(animationDuration)
                )
            }
        ){  HomeContent(paddingValues) }
        composable(
            BottomNavItem.TopSongs.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(animationDuration)
                )
                             },
            enterTransition = { slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(animationDuration)
            )}
        ){ TopSongsContent(paddingValues) }
        composable(
            BottomNavItem.TopArtists.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(animationDuration)
                )
            },
            enterTransition = { slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(animationDuration)
            )}
        ){ TopArtistsContent(paddingValues) }
    }
}