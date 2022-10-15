package montanez.alexander.spotme.ui.views.home

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import montanez.alexander.spotme.R
import montanez.alexander.spotme.ui.theme.SpotMeTheme
import montanez.alexander.spotme.ui.views.navigation.HomeNavGraph

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeView(navController: NavHostController = rememberAnimatedNavController()){
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) {
        HomeNavGraph(it,navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val items = listOf(
        BottomNavItem.TopSongs,
        BottomNavItem.Home,
        BottomNavItem.TopArtists,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar{
        items.forEach {
            NavigationBarItem(
                icon =  { Icon(ImageVector.vectorResource(id = it.icon),it.title) },
                label = { Text(it.title) },
                selected = it.route == currentRoute,
                onClick = {
                    navController.navigate(it.route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun HomeContainerPreview() {
    SpotMeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeView()
        }
    }
}

sealed class BottomNavItem(
    var title: String,
    var icon: Int,
    var route: String
){
    object Home: BottomNavItem("Home", R.drawable.ic_home,"HOME")
    object TopSongs: BottomNavItem("Top Songs", R.drawable.ic_music,"TOP_SONGS")
    object TopArtists: BottomNavItem("Top Artists", R.drawable.ic_microphone,"TOP_ARTISTS")
}
