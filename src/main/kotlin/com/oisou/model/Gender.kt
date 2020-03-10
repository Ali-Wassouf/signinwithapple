package com.oisou.model

import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "genders")
data class Gender(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column
    val name: String,
    @Column
    val description: String,
    @Column
    val enabled: Boolean,
    @Column
    val dateCreated: Timestamp,
    @Column
    val dateLastUpdated: Timestamp
)