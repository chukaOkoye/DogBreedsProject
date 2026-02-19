package com.example.dogbreedsproject.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.dogbreedsproject.presentation.dogbreedlist.DogBreedListScreen
import com.example.dogbreedsproject.presentation.dogbreedrandomimages.DogBreedRandomImageScreen
import kotlinx.serialization.Serializable

sealed class Routes{

    @Serializable
    data object DogBreedListRoute

    @Serializable
    data class DogBreedRandomImageRoute(val breedName: String)
}

@Composable
fun MainNavGraph(navController: NavHostController = rememberNavController()) {

    NavHost(navController = navController, startDestination = Routes.DogBreedListRoute){
        composable<Routes.DogBreedListRoute>{
            DogBreedListScreen(
                onNavigate = { name ->
                    navController.navigate(Routes.DogBreedRandomImageRoute(name))
                },
            )
        }

        composable<Routes.DogBreedRandomImageRoute>{ backStackEntry ->
            val breedName = backStackEntry.toRoute<Routes.DogBreedRandomImageRoute>().breedName
            DogBreedRandomImageScreen(
                breedName = breedName,
                onBackClick = {
                    navController.popBackStack()
                })

        }
    }

}