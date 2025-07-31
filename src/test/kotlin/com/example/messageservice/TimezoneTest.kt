package com.example.messageservice

import com.example.messageservice.entity.Message
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime
import java.time.ZoneOffset

class TimezoneTest {

    @Test
    fun `message should use UTC timezone`() {
        // Create a message
        val message = Message(
            sender = "test",
            content = "test content"
        )
        
        // Get current UTC time
        val nowUtc = LocalDateTime.now(ZoneOffset.UTC)
        
        // The timestamp should be close to current UTC time (within 1 second)
        assertNotNull(message.timestamp)
        assertTrue(message.timestamp.isAfter(nowUtc.minusSeconds(1)))
        assertTrue(message.timestamp.isBefore(nowUtc.plusSeconds(1)))
    }
} 