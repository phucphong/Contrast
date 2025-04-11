package com.itechpro.domain.model.navigationEvent


sealed class CustomerNavigationEvent {
    object GoToEdit : CustomerNavigationEvent()
    object GoToContact : CustomerNavigationEvent()
    object GoToOpportunity : CustomerNavigationEvent()
    object GoToTask : CustomerNavigationEvent()
    object GoToMore : CustomerNavigationEvent()
}


