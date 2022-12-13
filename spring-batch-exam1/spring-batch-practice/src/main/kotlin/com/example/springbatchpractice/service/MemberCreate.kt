package com.example.springbatchpractice.service

import com.example.springbatchpractice.domain.Member
import com.example.springbatchpractice.domain.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberCreate(
    private val memberRepository: MemberRepository,
) {

    @Transactional
    fun create(memberPrefix: Long) {
        memberRepository.save(
            Member("[$memberPrefix] unluckyjung")
        )
    }
}
