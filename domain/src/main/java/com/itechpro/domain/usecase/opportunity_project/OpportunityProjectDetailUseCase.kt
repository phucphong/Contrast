package com.itechpro.domain.usecase.opportunity_project
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity_project.OpportunityProject
import com.itechpro.domain.repository.opportunity_project.OpportunityProjectDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OpportunityProjectDetailUseCase @Inject constructor(
    private val repository: OpportunityProjectDetailRepository,

    ) {

    fun getOpportunitiesProjectDetail(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): Flow<NetworkResponse<OpportunityProject?>> {
        return flow {
            emit(NetworkResponse.Loading)

            when (val result = repository.getOpportunitiesProjectDetail(obj, mode, ido, authen)) {
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
