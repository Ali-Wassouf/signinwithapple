package com.oisou.model

import org.hibernate.annotations.Where
import java.sql.Timestamp
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "users")
@Where(clause = "deleted = false")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    var username: String,

    @Column(nullable = false)
    var dateOfBirth: Date,

    @Column(nullable = false)
    var countryCode: String,

    @Column(nullable = false)
    var appVersion: String,

    @ManyToOne
    @JoinColumn(name = "last_location", nullable = false)
    var lastLocation: Location,

    @Column(nullable = false)
    var deviceId: String,

    @Column(nullable = false, name = "apple_user_id")
    var appleUserIdentifier: String,

    @Column(nullable = false)
    var dateRegistration: Date,

    @Column(nullable = false)
    var dateLastProfileUpdate: Timestamp,

    @Column(nullable = false)
    var dateLastLogin: Timestamp,

    @Column(nullable = false)
    var dateDeletion: Date,

    @Column(nullable = false)
    var deleted: Boolean,
    @Column(nullable = false)
    var password: String
)
