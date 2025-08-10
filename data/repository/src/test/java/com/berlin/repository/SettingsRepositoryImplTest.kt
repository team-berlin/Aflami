package com.berlin.repository

import com.berlin.entity.AppLanguage
import com.berlin.entity.AppTheme
import com.berlin.repository.datasource.local.dataStore.SettingsLocalDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SettingsRepositoryImplTest {
    private lateinit var settingsDataStore: SettingsLocalDataSource
    private lateinit var settingsRepository: SettingsRepositoryImpl

    @Before
    fun setUp() {
        settingsDataStore = mockk(relaxed = true)
        settingsRepository = SettingsRepositoryImpl(settingsDataStore)
    }

    @Test
    fun `getTheme() should verify that SettingsLocalDatasource returns them name`() = runTest {
        val expectedTheme = flowOf("DARK")
        coEvery { settingsDataStore.getTheme() } returns expectedTheme
        val result = settingsRepository.getTheme()
        assertEquals(expectedTheme, result)
    }


    @Test
    fun `getLanguage() should verify that SettingsLocalDatasource returns language`() = runTest {
        val expectedLanguage = flowOf("AR")
        coEvery { settingsDataStore.getLanguage() } returns expectedLanguage
        val result = settingsRepository.getLanguage()
        assertEquals(expectedLanguage, result)
    }


    @Test
    fun `setLanguage() should verify that SettingsLocalDatasource set language`() = runTest {
        val language = AppLanguage.AR
        settingsRepository.setLanguage(language)
        coVerify { settingsDataStore.setLanguage(language.name) }
    }


    @Test
    fun `setTheme() should verify that SettingsLocalDatasource set theme`() = runTest {
        val theme = AppTheme.DARK
        settingsRepository.setTheme(theme)
        coVerify { settingsDataStore.setLanguage(theme.name) }
    }


}

