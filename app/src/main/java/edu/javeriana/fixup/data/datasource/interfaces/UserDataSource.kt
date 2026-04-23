package edu.javeriana.fixup.data.datasource.interfaces

import edu.javeriana.fixup.data.network.dto.UserDto

interface UserDataSource {
    suspend fun getUserById(userId: String): UserDto?
}
