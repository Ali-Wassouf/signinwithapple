package com.oisou.model.apple

import com.oisou.model.User
import com.oisou.model.enum.StateEnum
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs

@Entity
@Table(name = "apple_users_auth")
@TypeDefs(TypeDef(name = "jsonb", typeClass = JsonBinaryType::class))
data class AppleUsersAuth(
    @Id
    val id: String,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    var state: StateEnum,

    @Type(type = "jsonb")
    val authorized_scopes: String,

    @Type(type = "jsonb")
    val authorization_code: String,

    @Column(nullable = false)
    val identifyToken: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @Column(nullable = false)
    val realUserStatus: String

)
