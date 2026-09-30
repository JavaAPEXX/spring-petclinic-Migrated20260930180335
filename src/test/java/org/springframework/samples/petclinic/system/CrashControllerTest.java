package org.springframework.samples.petclinic.system;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CrashControllerTest {

    @InjectMocks
    private CrashController crashController;

    @Test
    @DisplayName("Given a crash controller instance, when triggerException is called, then a RuntimeException with expected message is thrown")
    void givenCrashControllerInstance_whenTriggerException_ThenRuntimeExceptionWithExpectedMessageIsThrown() {
        // Arrange
        String expectedMessage = "Expected: controller used to showcase what happens when an exception is thrown";

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            crashController.triggerException();
        });

        // Assert
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Given a crash controller instance, when triggerException is called multiple times, then a RuntimeException is thrown each time")
    void givenCrashControllerInstance_whenTriggerExceptionCalledMultipleTimes_ThenRuntimeExceptionThrownEachTime() {
        // Arrange
        int callCount = 3;

        // Act & Assert
        for (int i = 0; i < callCount; i++) {
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                crashController.triggerException();
            });
            assertNotNull(exception);
            assertEquals("Expected: controller used to showcase what happens when an exception is thrown", exception.getMessage());
        }
    }

    @Test
    @DisplayName("Given a crash controller instance, when triggerException is called, then the exception is an instance of RuntimeException")
    void givenCrashControllerInstance_whenTriggerException_ThenExceptionIsInstanceOfRuntimeException() {
        // Arrange
        // No specific setup needed as the controller has no dependencies

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            crashController.triggerException();
        });

        // Assert
        assertNotNull(exception);
        assertEquals(RuntimeException.class, exception.getClass());
    }
}