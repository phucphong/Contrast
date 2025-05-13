package com.contrast.Contrast.presentation.navigator.router.routes


sealed class RootRoutes(val route: String) {
    object HomeRoot : RootRoutes("homeRoot")
    object ReviewRoot : RootRoutes("reviewRoot")
    object AccountRoot : RootRoutes("accountRoot")
    object AffiliateRoot : RootRoutes("affiliateRoot")
    object Main : RootRoutes("main/{id}/{idUnit}/{introducerId}") {
        fun withArgs(id: String, idUnit: String, introducerId: String) =
            "main/$id/$idUnit/$introducerId"
    }
}
