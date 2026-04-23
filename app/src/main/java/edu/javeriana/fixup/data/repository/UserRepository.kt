package edu.javeriana.fixup.data.repository

import edu.javeriana.fixup.data.datasource.interfaces.UserDataSource
import edu.javeriana.fixup.data.mapper.toDomain
import edu.javeriana.fixup.ui.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDataSource: UserDataSource
) {
    fun getUserById(userId: String): Flow<Result<UserModel>> = flow {
        try {
            val userDto = userDataSource.getUserById(userId)
            if (userDto != null) {
                emit(Result.success(userDto.toDomain()))
            } else {
                emit(Result.failure(Exception("Usuario no encontrado")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
