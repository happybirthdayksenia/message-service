package com.example.messageservice.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "messages")
data class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(name = "sender", nullable = false)
    val sender: String,
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    val content: String,
    
    @Column(name = "timestamp", nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now()
) 