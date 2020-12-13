package me.ldrpontes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import me.ldrpontes.domain.entities.Goal
import me.ldrpontes.domain.entities.onFailure
import me.ldrpontes.domain.entities.onSuccess
import me.ldrpontes.domain.repositories.GoalsRepository

class GetGoalsUseCase(private val repository: GoalsRepository) :
    BaseUseCase<Flow<GetGoalsResponse>, GetGoalsParams> {

    override suspend fun execute(params: GetGoalsParams): Flow<GetGoalsResponse> {
        val result = repository.getGoals(params.token)

        return flow {

            result.collect {

                it.onSuccess { goals ->
                    emit(GetGoalsResponse(goals))
                }

                it.onFailure { exception ->
                    throw exception
                }

            }
        }
    }
}

data class GetGoalsParams(val token: String)

data class GetGoalsResponse(val result: List<Goal>)
