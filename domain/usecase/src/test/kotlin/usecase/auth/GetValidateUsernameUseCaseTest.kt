package usecase.auth

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GetValidateUsernameUseCaseTest {
    private val validateUsernameUseCase: GetValidateUsernameUseCase = GetValidateUsernameUseCase()

    @Test
    fun `should return true when username is valid`() {
        // Act
        val result = validateUsernameUseCase(VALID_USERNAME)

        // Assert
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when username is empty`() {
        // Act
        val result = validateUsernameUseCase(EMPTY_USERNAME)

        // Assert
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when username contains percent sign`() {
        // Act
        val result = validateUsernameUseCase(INVALID_USERNAME)

        // Assert
        assertThat(result).isFalse()
    }

    @Test
    fun `should return true when username contains other special characters`() {
        // Act
        val result = validateUsernameUseCase(SPECIAL_CHAR_USERNAME)

        // Assert
        assertThat(result).isTrue()
    }

    companion object{
        const val VALID_USERNAME = "nadeen123"
        const val INVALID_USERNAME = "nadee%n"
        const val EMPTY_USERNAME = ""
        const val SPECIAL_CHAR_USERNAME = "nadeen_123"
    }
}