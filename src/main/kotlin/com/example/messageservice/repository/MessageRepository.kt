package com.example.messageservice.repository

import com.example.messageservice.entity.Message
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface MessageRepository : JpaRepository<Message, Long> {
    
    fun findBySenderOrderByTimestampDesc(sender: String): List<Message>
    
    fun findByTimestampBetweenOrderByTimestampDesc(
        startTime: LocalDateTime, 
        endTime: LocalDateTime
    ): List<Message>
    
    @Query("SELECT m FROM Message m WHERE m.content LIKE %:keyword% ORDER BY m.timestamp DESC")
    fun findByContentContainingOrderByTimestampDesc(@Param("keyword") keyword: String): List<Message>
    
    fun findByTimestampBetweenOrderByTimestampAsc(
        timestampAfter: LocalDateTime,
        timestampBefore: LocalDateTime,
        sort: Sort
    ): MutableList<Message>
} 