package com.oisou.service

import com.oisou.model.Gender
import com.oisou.repository.GenderRepository
import org.springframework.stereotype.Service

@Service
class GenderService(private val genderRepository: GenderRepository) {

    fun findGender(id: Long): Gender = genderRepository.getOne(id)
}