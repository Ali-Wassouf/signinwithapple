package com.oisou.model

import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "locations")
data class Location(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    var latitude: Float,

    @Column(nullable = false)
    var longitude: Float,
    @Column
    val dateCreated: Timestamp,
    @Column
    val dateLastUpdated: Timestamp
) {
    constructor() : this(-1L, 0.0F, 0.0F, Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()))
}
