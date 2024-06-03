package ru.anydevprojects.aiassistant.feature.registration.presentation

import androidx.lifecycle.ViewModel
import ru.anydevprojects.aiassistant.feature.registration.domain.RegistrationRepository

class RegistrationViewModel(
    private val registrationRepository: RegistrationRepository
) : ViewModel()
