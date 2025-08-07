package usecase.auth

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class GetValidatePasswordUseCaseTest {
    private lateinit var validatePasswordUseCase: GetValidatePasswordUseCase

    @Before
    fun setup() {
        validatePasswordUseCase = GetValidatePasswordUseCase()
    }

    @Test
    fun `should return true when password is valid`() {
        val result = validatePasswordUseCase(VALID_PASSWORD)
        assertTrue(result)
    }

    @Test
    fun `should return false when password is empty`() {
        val result = validatePasswordUseCase(INVALID_PASSWORD)
        assertFalse(result)
    }

    @Test
    fun `should return false when password is less than 4 characters`() {
        val result = validatePasswordUseCase(SHORT_PASSWORD)
        assertFalse(result)
    }

    @Test
    fun `should return true when password is longer than 4 characters`() {
        val result = validatePasswordUseCase(LONG_PASSWORD)
        assertTrue(result)
    }

    companion object{
        const val VALID_PASSWORD = "1234"
        const val INVALID_PASSWORD = ""
        const val SHORT_PASSWORD = "abc"
        const val LONG_PASSWORD = "abcdef"
    }

}