package com.itechpro.domain.model.profile

import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.SplashNaEvent


sealed class ProfileNaEvent : NavEvent {








    data class GoToShareProduct(

        val title: String,
    ) : ProfileNaEvent()



    data class GoToInCome(

        val title: String,
    ) : ProfileNaEvent()



    data class GoToShareQrcode(

        val title: String,
    ) : ProfileNaEvent()


    data class GoToAgency(

        val title: String,
    ) : ProfileNaEvent()



    data class GoToServiceProgress(

        val title: String,
    ) : ProfileNaEvent()

    data class GoToServiceCalendar(

        val title: String,
    ) : ProfileNaEvent()

    data class GoToSpaAtHome(

        val title: String,
    ) : ProfileNaEvent()




    data class GoToProductViewSave(
        val type: String,
        val title: String,
    ) : ProfileNaEvent()







    object None : ProfileNaEvent() // trạng thái mặc định
}
