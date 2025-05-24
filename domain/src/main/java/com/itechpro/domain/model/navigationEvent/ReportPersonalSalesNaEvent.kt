package com.itechpro.domain.model.navigationEvent


import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.SplashNaEvent


sealed class ReportPersonalSalesNaEvent : NavEvent {








    data class GoToPersonalConsumptionSales(

        val title: String,
    ) : ReportPersonalSalesNaEvent()

    data class GoToUpToLevelSales(

        val type: String,
        val title: String,
    ) : ReportPersonalSalesNaEvent()


    data class GoToSalesReportByAgency(

        val title: String,
    ) : ReportPersonalSalesNaEvent()

    data class GoToPassiveCommissionReport(

        val title: String,
    ) : ReportPersonalSalesNaEvent()





    data class GoToRankAdvancementBonusReport(
        val type: String,
        val title: String,
    ) : ReportPersonalSalesNaEvent()



    object None : ReportPersonalSalesNaEvent() // trạng thái mặc định
}
