package com.oisou.model

import org.hibernate.annotations.Where
import java.sql.Timestamp
import java.util.Date
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "users")
@Where(clause = "deleted = false")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false, name = "auth_provider_user_id")
    var username: String,

    @Column(nullable = false)
    var dateOfBirth: Date,

    @Column(nullable = false)
    var countryCode: String,

    @Column(nullable = false)
    var appVersion: String,

//    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
//    @JoinColumn(name = "last_location", nullable = false)
//    var lastLocation: Location,

    @Column(nullable = false)
    var deviceId: String,

    //todo delete
    @Column(nullable = false, name = "apple_user_id")
    var appleUserIdentifier: String,

    @Column(nullable = false)
    var dateRegistration: Date,

    @Column
    var dateLastProfileUpdate: Timestamp?,

    @Column
    var dateLastLogin: Timestamp?,

    @Column
    var dateDeletion: Date?,

    @Column(nullable = false)
    var deleted: Boolean,
    @Column(nullable = false, name = "auth_provider_token")
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: Role?,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "gender", referencedColumnName = "id")
    var gender: Gender?,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "gender_of_interest", referencedColumnName = "id")
    var genderOfInterest: Gender?,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    var authKey: AuthKey,

    @Column(name="name")
    var name: String

)
