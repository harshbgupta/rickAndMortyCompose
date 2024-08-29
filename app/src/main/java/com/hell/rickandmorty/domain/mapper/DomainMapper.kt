package com.hell.rickandmorty.domain.mapper

interface DomainMapper<Dto, DomainModel> {
    fun mapToDomainModel(data: Dto?): DomainModel
    fun toDomainList(initial: List<Dto?>?): List<DomainModel>
}