package com.example.messageservice.dto

import com.example.messageservice.entity.Message
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class CreateMessageRequest(
    @field:NotBlank(message = "Sender is required")
    @field:Size(min = 1, max = 100, message = "Sender must be between 1 and 100 characters")
    val sender: String,
    
    @field:NotBlank(message = "Content is required")
    @field:Size(min = 1, max = 1000, message = "Content must be between 1 and 1000 characters")
    val content: String
)

data class CreateTimedMessageRequest(
    @field:NotBlank(message = "Sender is required")
    @field:Size(min = 1, max = 100, message = "Sender must be between 1 and 100 characters")
    val sender: String,
    
    @field:NotBlank(message = "Content is required")
    @field:Size(min = 1, max = 1000, message = "Content must be between 1 and 1000 characters")
    val content: String,
    
    val timestamp: LocalDateTime
)

data class MessageResponse(
    val id: Long,
    val sender: String,
    val content: String,
    val timestamp: LocalDateTime
) {
    companion object {
        fun fromEntity(message: Message): MessageResponse {
            return MessageResponse(
                id = message.id!!,
                sender = message.sender,
                content = message.content,
                timestamp = message.timestamp
            )
        }
    }
} 