package com.example.springbatchpractice.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun MemberRepository.findByMemberId(id: Long): Member =
    findByIdOrNull(id) ?: throw NoSuchElementException("member id가 존재하지 않습니다. id: $id")

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByName(name: String): Member?
    fun existsByName(name: String): Boolean
}
