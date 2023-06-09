package com.example.wallpaperapp.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wallpaperapp.R

@Composable
fun BottomNavigationBar(navController: NavController) {

    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Favourites,
        NavigationItem.Downloads,
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.main_theme),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = ""//item.title
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to avoid building up a large stack of destinations on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true // // Avoid multiple copies of the same destination when reselecting the same item
                        restoreState = true // Restore state when reselecting a previously selected item
                    }
                }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {

    //BottomNavigationBar(null)

}