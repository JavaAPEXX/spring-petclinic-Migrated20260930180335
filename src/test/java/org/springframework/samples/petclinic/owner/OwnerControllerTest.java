```java
package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private WebDataBinder dataBinder;

    @InjectMocks
    private OwnerController ownerController;

    private Owner validOwner;

    @BeforeEach
    void setUp() {
        validOwner = new Owner();
        validOwner.setId(1);
        validOwner.setFirstName("John");
        validOwner.setLastName("Doe");
        validOwner.setAddress("123 Main St");
        validOwner.setCity("Springfield");
        validOwner.setTelephone("123-456-7890");
    }

    @Test
    @DisplayName("Given valid owner ID, when finding owner, then return owner")
    void givenValidOwnerId_whenFindOwner_thenReturnOwner() {
        // Arrange
        Integer ownerId = 1;
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(validOwner));

        // Act
        Owner result = ownerController.findOwner(ownerId);

        // Assert
        assertNotNull(result);
        assertEquals(validOwner, result);
        verify(ownerRepository, times(1)).findById(ownerId);
    }

    @Test
    @DisplayName("Given null owner ID, when finding owner, then return new owner")
    void givenNullOwnerId_whenFindOwner_thenReturnNewOwner() {
        // Arrange
        Integer ownerId = null;

        // Act
        Owner result = ownerController.findOwner(ownerId);

        // Assert
        assertNotNull(result);
        assertEquals(new Owner(), result);
        verify(ownerRepository, never()).findById(anyInt());
    }

    @Test
    @DisplayName("Given non-existent owner ID, when finding owner, then throw IllegalArgumentException")
    void givenNonExistentOwnerId_whenFindOwner_thenThrowIllegalArgumentException() {
        // Arrange
        Integer ownerId = 999;
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ownerController.findOwner(ownerId));
        assertTrue(exception.getMessage().contains("Owner not found with id: