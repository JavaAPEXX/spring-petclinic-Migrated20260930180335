```java
package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    private OwnerRepository owners;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private WebDataBinder webDataBinder;

    @InjectMocks
    private VisitController visitController;

    private Owner owner;
    private Pet pet;
    private Visit visit;
    private Map<String, Object> model;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(1);
        owner.setFirstName("John");
        owner.setLastName("Doe");

        pet = new Pet();
        pet.setId(10);
        pet.setName("Rex");
        pet.setOwner(owner);
        owner.addPet(pet);

        visit = new Visit();
        visit.setDate(LocalDate.now().plusDays(1));
        visit.setDescription("Checkup");

        model = new HashMap<>();
    }

    @Test
    @DisplayName("Given valid owner and pet, when loading visit model, then return new visit and populate model")
    void givenValidOwnerAndPet_whenLoadPetWithVisit_thenReturnNewVisitAndPopulateModel() {
        // Arrange
        int ownerId = 1;
        int petId = 10;
        when(owners.findById(ownerId)).thenReturn(Optional.of(owner));

        // Act
        Visit result = visitController.loadPetWithVisit(ownerId, petId, model);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof Visit);
        assertEquals(pet, model.get("pet"));
        assertEquals(owner, model.get("owner"));
        verify(owners, times(1)).findById(ownerId);
    }

    @Test
    @DisplayName("Given non-existent owner, when loading visit model, then throw IllegalArgumentException")
    void givenNonExistentOwner_whenLoadPetWithVisit_thenThrowIllegalArgumentException() {
        // Arrange
        int ownerId = 999;
        int petId = 10;
        when(owners.findById(ownerId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> visitController.loadPetWithVisit(ownerId, petId, model));
        assertTrue(exception.getMessage().contains("Owner not found with id: " + ownerId));
        verify(owners, times(1)).findById(ownerId);
    }

    @Test
    @DisplayName("Given owner without specified pet, when loading visit model, then throw IllegalArgumentException