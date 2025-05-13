package com.contrast.Contrast.presentation.navigator.router.routes

import androidx.navigation.NavType
import androidx.navigation.navArgument

object MediaRoutes {
    object MediaPicker {
        const val route = "media_picker/{maxCount}/{allowImage}/{allowVideo}/{compressedFiles}"
        val arguments = listOf(
            navArgument("maxCount") { type = NavType.IntType },
            navArgument("allowImage") { type = NavType.BoolType },
            navArgument("allowVideo") { type = NavType.BoolType },
            navArgument("compressedFiles") { type = NavType.BoolType }
        )
        fun withArgs(maxCount: Int, allowImage: Boolean, allowVideo: Boolean, compressedFiles: Boolean): String {
            return "media_picker/$maxCount/$allowImage/$allowVideo/$compressedFiles"
        }
    }
}