package com.android.candywords.navigation

enum class MainNavGraph(val route: String) {
    Launcher(route = "launcher"),
    Menu(route = "menu")
}

enum class GameNavGraph(val route: String) {
    GAME(route = "game"),
    LOBBY(route = "lobby")
}