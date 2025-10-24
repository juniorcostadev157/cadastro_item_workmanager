package com.junior.projetomvvmcleanxml.domain.model.user

import com.junior.projetomvvmcleanxml.data.model.user.UserEntity

fun Users.toEntity() = UserEntity(
    id = this.id,
    nome = this.nome,
    email = this.email
)