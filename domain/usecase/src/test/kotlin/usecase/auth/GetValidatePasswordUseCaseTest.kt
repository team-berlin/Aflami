package usecase.auth

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GetValidatePasswordUseCaseTest {
    private val validatePasswordUseCase: GetValidatePasswordUseCase  = GetValidatePasswordUseCase()

    @Test
    fun `should return true when password is valid`() {
        // Act
        val result = validatePasswordUseCase(VALID_PASSWORD)

        // Assert
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when password is empty`() {
        // Act
        val result = validatePasswordUseCase(EMPTY_PASSWORD)

        // Assert
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when password is less than 4 characters`() {
        // Act
        val result = validatePasswordUseCase(SHORT_PASSWORD)

        // Assert
        assertThat(result).isFalse()
    }

    @Test
    fun `should return true when password is longer than 4 characters`() {
        // Act
        val result = validatePasswordUseCase(LONG_PASSWORD)

        // Assert
        assertThat(result).isTrue()
    }

    companion object{
        const val VALID_PASSWORD = "1234"
        const val EMPTY_PASSWORD = ""
        const val SHORT_PASSWORD = "abc"
        const val LONG_PASSWORD = "abcdef"
    }
}