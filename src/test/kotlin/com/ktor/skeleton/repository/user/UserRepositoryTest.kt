package com.ktor.skeleton.repository.user

import com.ktor.skeleton.helper.clearH2DatabaseFiles
import com.ktor.skeleton.helper.initH2DatabaseConnection
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*

class UserRepositoryTest {
    companion object {
        private lateinit var userRepository: UserRepository

        @JvmStatic
        @BeforeAll
        fun setUp() {
            initH2DatabaseConnection()
            userRepository = UserRepository()
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            clearAllMocks()
            clearH2DatabaseFiles()
        }
    }


    @Test
    fun testUserRepository_list_ReturnListUserModels() = runBlocking {
        val listUsers = userRepository.list()
        val username1 = "john_doe"
        val username2 = "jane_smith"
        val username3 = "alex_brown"

        Assertions.assertEquals(listUsers.size, 3)
        Assertions.assertEquals(listUsers[1].username, username2)
        Assertions.assertEquals(listUsers[2].username, username3)
    }
}
