package com.oisou.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "auth_keys")
data class AuthKey(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,

    @Column(name = "refresh_token", nullable = false)
    var refreshToken: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    var authProvider: ProviderEnum,

    @Column(name = "is_valid")
    var isValid: Boolean?

)

