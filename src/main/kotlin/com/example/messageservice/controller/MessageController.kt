package com.example.messageservice.controller

import com.example.messageservice.dto.CreateBirthdayMessageRequest
import com.example.messageservice.dto.CreateMessageRequest
import com.example.messageservice.dto.MessageResponse
import com.example.messageservice.service.MessageService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/api/messages")
class MessageController(private val messageService: MessageService) {
    
    @PostMapping
    fun createMessage(@Valid @RequestBody request: CreateMessageRequest): ResponseEntity<MessageResponse> {
        val message = messageService.createMessage(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(message)
    }

    @PostMapping("/birthday")
    fun createBirthdayMessage(@Valid @RequestBody request: CreateBirthdayMessageRequest): ResponseEntity<MessageResponse> {
        val message = messageService.createBirthdayMessage(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(message)
    }

    @PostMapping("/live")
    fun createBirthdayMessage(@Valid @RequestBody request: CreateMessageRequest): ResponseEntity<MessageResponse> {
        val message = messageService.createLiveMessage(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(message)
    }
    
    @GetMapping
    fun getAllMessages(): ResponseEntity<List<MessageResponse>> {
        val messages = messageService.getAllMessages()
        return ResponseEntity.ok(messages)
    }
    
    @GetMapping("/{id}")
    fun getMessageById(@PathVariable id: Long): ResponseEntity<MessageResponse> {
        val message = messageService.getMessageById(id)
        return if (message != null) {
            ResponseEntity.ok(message)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/sender/{sender}")
    fun getMessagesBySender(@PathVariable sender: String): ResponseEntity<List<MessageResponse>> {
        val messages = messageService.getMessagesBySender(sender)
        return ResponseEntity.ok(messages)
    }
    
    @GetMapping("/search")
    fun searchMessagesByContent(@RequestParam keyword: String): ResponseEntity<List<MessageResponse>> {
        val messages = messageService.searchMessagesByContent(keyword)
        return ResponseEntity.ok(messages)
    }
    
    @GetMapping("/timerange")
    fun getMessagesByTimeRange(
        @RequestParam startTime: String,
        @RequestParam endTime: String
    ): ResponseEntity<List<MessageResponse>> {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val start = LocalDateTime.parse(startTime, formatter)
        val end = LocalDateTime.parse(endTime, formatter)
        
        val messages = messageService.getMessagesByTimeRange(start, end)
        return ResponseEntity.ok(messages)
    }
    
    @DeleteMapping("/{id}")
    fun deleteMessage(@PathVariable id: Long): ResponseEntity<Void> {
        val deleted = messageService.deleteMessage(id)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
} 