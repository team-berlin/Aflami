package usecase.auth

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class GetValidateUsernameUseCaseTest {
    private lateinit var validateUsernameUseCase: GetValidateUsernameUseCase

    @Before
    fun setup() {
        validateUsernameUseCase = GetValidateUsernameUseCase()
    }

    @Test
    fun `should return true when username is valid`() {
        val result = validateUsernameUseCase(VALID_USERNAME)
        assertTrue(result)
    }

    @Test
    fun `should return false when username is empty`() {
        val result = validateUsernameUseCase(EMPTY_USERNAME)
        assertFalse(result)
    }

    @Test
    fun `should return false when username contains percent sign`() {
        val result = validateUsernameUseCase(INVALID_USERNAME)
        assertFalse(result)
    }

    @Test
    fun `should return true when username contains other special characters`() {
        val result = validateUsernameUseCase(SPECIAL_CHAR_USERNAME)
        assertTrue(result)
    }

    companion object{
        const val VALID_USERNAME = "nadeen123"
        const val INVALID_USERNAME = "nadee%n"
        const val EMPTY_USERNAME = ""
        const val SPECIAL_CHAR_USERNAME = "nadeen_123"
    }
}