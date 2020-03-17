package com.oisou.model

import com.vividsolutions.jts.geom.Point
import org.hibernate.annotations.Where
import org.hibernate.envers.Audited
import org.hibernate.envers.RelationTargetAuditMode
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

    @Column(nullable = true)
    var lastLocation: Point?,

    @Column
    var dateLastLogin: Timestamp?,

    @Column
    var dateDeleted: Timestamp?,

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
    @JoinColumn(name = "auth_keys_id", referencedColumnName = "id")
    var authKey: AuthKey,

    @Column(name="name")
    var name: String,
    @Column
    val dateCreated: Timestamp,
    @Column
    val dateLastUpdated: Timestamp

)
