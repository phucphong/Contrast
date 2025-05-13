package com.contrast.Contrast.presentation.navigator.router.routes


import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

object ReviewRoutes {
    object ReviewRoot {
        const val route = "review_root"
    }

    object AddReview {
        const val route = "add_review/{id}/{idUnit}/{fileTxt}/{name}"
        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("idUnit") { type = NavType.StringType },
            navArgument("fileTxt") { type = NavType.StringType },
            navArgument("name") { type = NavType.StringType }
        )

        fun withArgs(id: String, idUnit: String, fileTxt: String, name: String): String {
            return "add_review/${Uri.encode(id)}/${Uri.encode(idUnit)}/${Uri.encode(fileTxt)}/${Uri.encode(name)}"
        }
    }



    object MediaPicker {
        const val route = "media_picker/{maxCount}/{allowImage}/{allowVideo}/{compressedFiles}/{parentRoute}"

        val arguments = listOf(
            navArgument("maxCount") { type = NavType.IntType },
            navArgument("allowImage") { type = NavType.BoolType },
            navArgument("allowVideo") { type = NavType.BoolType },
            navArgument("compressedFiles") { type = NavType.BoolType },
            navArgument("parentRoute") { type = NavType.StringType }
        )

        fun withArgs(
            maxCount: Int,
            allowImage: Boolean,
            allowVideo: Boolean,
            compressedFiles: Boolean,
            parentRoute: String
        ): String {
            return "media_picker/$maxCount/$allowImage/$allowVideo/$compressedFiles/$parentRoute"
        }
    }




    object Reviews {
        const val baseRoute = "product_Reviews"
        const val route = "$baseRoute/{id}"
        fun withArgs(id: String): String {
            return "$baseRoute/${Uri.encode(id)}"
        }
        val arguments = listOf(
            navArgument("id") { type = NavType.StringType }
        )
    }


}
