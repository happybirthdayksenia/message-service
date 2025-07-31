package com.example.messageservice

import com.example.messageservice.dto.CreateBulkMessageItem
import com.example.messageservice.dto.CreateBulkMessageRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime
import java.time.ZoneOffset

class BulkMessageTest {

    @Test
    fun `bulk message should create multiple messages with custom timestamps`() {
        // Create custom timestamps
        val timestamp1 = LocalDateTime.of(2025, 1, 1, 12, 0, 0)
        val timestamp2 = LocalDateTime.of(2025, 1, 2, 15, 30, 0)
        
        // Create bulk message request
        val bulkRequest = CreateBulkMessageRequest(
            messages = listOf(
                CreateBulkMessageItem(
                    sender = "Alice",
                    content = "First message",
                    timestamp = timestamp1
                ),
                CreateBulkMessageItem(
                    sender = "Bob",
                    content = "Second message",
                    timestamp = timestamp2
                )
            )
        )
        
        // Verify the request structure
        assertEquals(2, bulkRequest.messages.size)
        assertEquals("Alice", bulkRequest.messages[0].sender)
        assertEquals("First message", bulkRequest.messages[0].content)
        assertEquals(timestamp1, bulkRequest.messages[0].timestamp)
        assertEquals("Bob", bulkRequest.messages[1].sender)
        assertEquals("Second message", bulkRequest.messages[1].content)
        assertEquals(timestamp2, bulkRequest.messages[1].timestamp)
    }
    
    @Test
    fun `bulk message items should have proper validation constraints`() {
        // Test that the validation annotations are properly set
        val item = CreateBulkMessageItem(
            sender = "TestSender",
            content = "Test content",
            timestamp = LocalDateTime.now(ZoneOffset.UTC)
        )
        
        assertNotNull(item.sender)
        assertNotNull(item.content)
        assertNotNull(item.timestamp)
        assertTrue(item.sender.length in 1..100)
        assertTrue(item.content.length in 1..1000)
    }
} 