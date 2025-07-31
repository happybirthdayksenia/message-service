package com.example.messageservice

import com.example.messageservice.dto.CreateMessageRequest
import com.example.messageservice.service.MessageService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class DeleteAllTest {

    @Autowired
    private lateinit var messageService: MessageService

    @Test
    fun `deleteAllMessages should delete all messages and return count`() {
        // Create some test messages
        val message1 = messageService.createMessage(
            CreateMessageRequest(
                sender = "TestUser1",
                content = "Test message 1"
            )
        )
        val message2 = messageService.createMessage(
            CreateMessageRequest(
                sender = "TestUser2",
                content = "Test message 2"
            )
        )
        
        // Verify messages were created
        val allMessages = messageService.getReallyAllMessages()
        assertEquals(2, allMessages.size)
        
        // Delete all messages
        val deletedCount = messageService.deleteAllMessages()
        
        // Verify the count returned is correct
        assertEquals(2L, deletedCount)
        
        // Verify all messages are deleted
        val remainingMessages = messageService.getReallyAllMessages()
        assertEquals(0, remainingMessages.size)
    }
    
    @Test
    fun `deleteAllMessages should return 0 when no messages exist`() {
        // Ensure no messages exist
        val initialMessages = messageService.getReallyAllMessages()
        assertEquals(0, initialMessages.size)
        
        // Delete all messages
        val deletedCount = messageService.deleteAllMessages()
        
        // Verify count is 0
        assertEquals(0L, deletedCount)
        
        // Verify still no messages
        val remainingMessages = messageService.getReallyAllMessages()
        assertEquals(0, remainingMessages.size)
    }
} 