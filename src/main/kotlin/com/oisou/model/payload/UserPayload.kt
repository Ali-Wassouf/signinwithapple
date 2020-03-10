package com.oisou.model.payload

import java.util.Date

data class UserPayload(val name: String,
                       val authProviderId: String,
                       val authProviderToken: String,
                       val appVersion: String,
                       val genderId: Long,
                       val genderOfInterestId: Long,
                       val dateOfBirth: Date,
                       val countryCode: String,
                       val location: Location)