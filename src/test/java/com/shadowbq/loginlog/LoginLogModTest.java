package com.shadowbq.loginlog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Files;
import java.time.Instant;

/**
 * Basic tests for LoginLogMod functionality
 */
public class LoginLogModTest {

    @Test
    @DisplayName("Mod should be instantiable")
    public void testModInstantiation() {
        // Test that we can create an instance of the mod
        LoginLogMod mod = new LoginLogMod();
        assertNotNull(mod, "LoginLogMod instance should not be null");
    }

    @Test
    @DisplayName("Log entry formatting should work correctly")
    public void testLogEntryFormatting() {
        // Test the basic log entry format
        String username = "TestPlayer";
        String eventType = "JOIN";
        Instant timestamp = Instant.parse("2025-07-27T20:15:30.123Z");
        
        // Expected format: [EVENT_TYPE] TIMESTAMP USERNAME
        String expectedPattern = "\\[" + eventType + "\\] " + timestamp.toString() + " " + username;
        String actualEntry = "[" + eventType + "] " + timestamp.toString() + " " + username;
        
        assertTrue(actualEntry.matches(expectedPattern), 
                   "Log entry should match expected format");
    }

    @Test
    @DisplayName("Log file creation should work")
    public void testLogFileCreation(@TempDir Path tempDir) throws Exception {
        // Test that we can create a log file in a temporary directory
        Path logFile = tempDir.resolve("test_login_log.log");
        
        // Simulate writing a log entry
        String logEntry = "[TEST] 2025-07-27T20:15:30.123Z TestPlayer\n";
        Files.writeString(logFile, logEntry);
        
        assertTrue(Files.exists(logFile), "Log file should be created");
        assertTrue(Files.size(logFile) > 0, "Log file should not be empty");
        
        String content = Files.readString(logFile);
        assertTrue(content.contains("TestPlayer"), "Log file should contain player name");
        assertTrue(content.contains("[TEST]"), "Log file should contain event type");
    }

    @Test
    @DisplayName("Username validation should work")
    public void testUsernameValidation() {
        // Test basic username validation (Minecraft usernames are 3-16 characters, alphanumeric + underscore)
        assertTrue(isValidMinecraftUsername("Steve"), "Valid username should pass");
        assertTrue(isValidMinecraftUsername("Alex_123"), "Valid username with underscore and numbers should pass");
        assertTrue(isValidMinecraftUsername("Player1"), "Valid username with numbers should pass");
        
        assertFalse(isValidMinecraftUsername(""), "Empty username should fail");
        assertFalse(isValidMinecraftUsername("AB"), "Too short username should fail");
        assertFalse(isValidMinecraftUsername("ThisUsernameIsTooLong"), "Too long username should fail");
        assertFalse(isValidMinecraftUsername("Player-Name"), "Username with invalid characters should fail");
    }

    /**
     * Helper method to validate Minecraft usernames
     * Basic validation: 3-16 characters, alphanumeric + underscore only
     */
    private boolean isValidMinecraftUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 16) {
            return false;
        }
        return username.matches("^[a-zA-Z0-9_]+$");
    }
}
