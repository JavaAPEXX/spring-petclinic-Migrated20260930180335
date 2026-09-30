/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.GetMapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
class WelcomeControllerTest {

    @InjectMocks
    private WelcomeController welcomeController;

    @BeforeEach
    void setUp() {
        // No setup required as WelcomeController has no dependencies
    }

    @Test
    @DisplayName("Given a valid request to root path, when welcome method is called, then it should return 'welcome' view name")
    void givenValidRequest_whenWelcomeMethodCalled_thenReturnWelcomeView() {
        // Arrange
        // No specific arrangement needed for this simple controller

        // Act
        String viewName = welcomeController.welcome();

        // Assert
        assertEquals("welcome", viewName);
        assertNotNull(viewName);
    }

    @Test
    @DisplayName("Given the controller instance, when checking method annotation, then it should have GetMapping for root path")
    void givenControllerInstance_whenCheckingGetMappingAnnotation_thenShouldHaveRootPathMapping() {
        // Arrange
        // No specific arrangement needed

        // Act
        GetMapping mapping = welcomeController.getClass().getMethods()[0].getAnnotation(GetMapping.class);

        // Assert
        assertNotNull(mapping);
        assertEquals("/", mapping.value()[0]);
    }

    @Test
    @DisplayName("Given multiple calls to welcome method, when invoked repeatedly, then it should consistently return 'welcome'")
    void givenMultipleInvocations_whenWelcomeMethodCalledRepeatedly_thenShouldConsistentlyReturnWelcome() {
        // Arrange
        // No specific arrangement needed

        // Act
        String firstResult = welcomeController.welcome();
        String secondResult = welcomeController.welcome();
        String thirdResult = welcomeController.welcome();

        // Assert
        assertEquals("welcome", firstResult);
        assertEquals("welcome", secondResult);
        assertEquals("welcome", thirdResult);
        assertSame(firstResult, secondResult);
        assertSame(secondResult, thirdResult);
    }

    @Test
    @DisplayName("Given the controller, when verifying return type, then it should return a non-empty string")
    void givenController_whenVerifyingReturnType_thenShouldReturnNonEmptyString() {
        // Arrange
        // No specific arrangement needed

        // Act
        String result = welcomeController.welcome();

        // Assert
        assertNotNull(result);
        assertEquals(7, result.length());
        assertEquals("welcome", result);
    }
}