package com.oisou.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "devices")
data class Device(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    val serialNumber: String,

    @Column(nullable = false)
    val advertisingId: String,

    @Column(nullable = false)
    val deviceOs: String,

    @Column(nullable = false)
    val osVersion: String,

    @Column(nullable = false)
    val model: String,

    @Column(nullable = false)
    val language: String,

    @Column(nullable = false)
    val pushNotification: Boolean,

    @Column(nullable = false)
    val pushToken: String
)
