package montanez.alexander.spotme

sealed class Routes(val route: String){
    object Login: Routes("Login")
    object Home: Routes("Home")
}
