package com.itechpro.domain.usecase.opportunity
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity.Opportunity
import com.itechpro.domain.repository.opportunity.OpportunityDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OpportunityDetailUseCase @Inject constructor(
    private val repository: OpportunityDetailRepository,

    ) {

    fun getOpportunityDetail(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): Flow<NetworkResponse<Opportunity?>> {
        return flow {
            emit(NetworkResponse.Loading)

            when (val result = repository.getOpportunityDetail(obj, mode, ido, authen)) {
                is NetworkResponse.Success -> {
                    val opportunity = result.data.firstOrNull()
                    emit(NetworkResponse.Success(opportunity))
                }
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }
                else -> {
                    emit(NetworkResponse.Error("Unknown error"))
                }
            }
        }.flowOn(Dispatchers.IO)
    }





}
