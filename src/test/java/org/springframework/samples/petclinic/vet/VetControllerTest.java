package org.springframework.samples.petclinic.vet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VetControllerTest {

    @InjectMocks
    private VetController vetController;


    @Test
    @DisplayName("Test VetController instance initialization")
    public void testInstance_NotNull() {
        assertNotNull(vetController);
    }

}
