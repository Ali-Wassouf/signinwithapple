package com.oisou.model

import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "system_auth_keys")
data class AuthKey(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "refresh_token", nullable = false)
    var refreshToken: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    var authProvider: ProviderEnum,

    @Column(name = "is_valid")
    var isValid: Boolean?,

    @Column(name = "date_created")
    var dateCreated: Timestamp,

    @Column(name = "date_last_updated")
    var dateLastUpdated: Timestamp

)

