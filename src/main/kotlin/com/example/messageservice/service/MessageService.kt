package com.example.messageservice.service

import com.example.messageservice.dto.CreateBirthdayMessageRequest
import com.example.messageservice.dto.CreateMessageRequest
import com.example.messageservice.dto.MessageResponse
import com.example.messageservice.entity.Message
import com.example.messageservice.repository.MessageRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class MessageService(private val messageRepository: MessageRepository) {
    
    fun createMessage(request: CreateMessageRequest): MessageResponse {
        val message = Message(
            sender = request.sender,
            content = request.content
        )
        val savedMessage = messageRepository.save(message)
        return MessageResponse.fromEntity(savedMessage)
    }

    fun createBirthdayMessage(request: CreateBirthdayMessageRequest): MessageResponse {
        val message = Message(
            sender = request.sender,
            content = request.content,
            timestamp = request.timestamp
        )
        val savedMessage = messageRepository.save(message)
        return MessageResponse.fromEntity(savedMessage)
    }

    fun createLiveMessage(request: CreateMessageRequest): MessageResponse {
        val message = Message(
            sender = request.sender,
            content = request.content,
            timestamp = LocalDateTime.now().plusHours(7)
        )
        val savedMessage = messageRepository.save(message)
        return MessageResponse.fromEntity(savedMessage)
    }
    
    @Transactional(readOnly = true)
    fun getAllMessages(): List<MessageResponse> {
        return messageRepository.findByTimestampBetweenOrderByTimestampAsc(LocalDateTime.now().minusDays(365), LocalDateTime.now(), Sort.by(Sort.Order.asc("timestamp")))
            .map { MessageResponse.fromEntity(it) }
    }
    
    @Transactional(readOnly = true)
    fun getMessageById(id: Long): MessageResponse? {
        return messageRepository.findById(id)
            .map { MessageResponse.fromEntity(it) }
            .orElse(null)
    }
    
    @Transactional(readOnly = true)
    fun getMessagesBySender(sender: String): List<MessageResponse> {
        return messageRepository.findBySenderOrderByTimestampDesc(sender)
            .map { MessageResponse.fromEntity(it) }
    }
    
    @Transactional(readOnly = true)
    fun getMessagesByTimeRange(startTime: LocalDateTime, endTime: LocalDateTime): List<MessageResponse> {
        return messageRepository.findByTimestampBetweenOrderByTimestampDesc(startTime, endTime)
            .map { MessageResponse.fromEntity(it) }
    }
    
    @Transactional(readOnly = true)
    fun searchMessagesByContent(keyword: String): List<MessageResponse> {
        return messageRepository.findByContentContainingOrderByTimestampDesc(keyword)
            .map { MessageResponse.fromEntity(it) }
    }
    
    fun deleteMessage(id: Long): Boolean {
        return if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id)
            true
        } else {
            false
        }
    }
} 