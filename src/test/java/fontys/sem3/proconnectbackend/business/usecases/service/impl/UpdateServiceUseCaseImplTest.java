package fontys.sem3.proconnectbackend.business.usecases.service.impl;

import fontys.sem3.proconnectbackend.business.dtos.UpdateServiceRequest;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UpdateServiceUseCaseImplTest {
    /**
     * @verifies return Optional id if the service is present
     * @see UpdateServiceUseCaseImpl#updateService(fontys.sem3.proconnectbackend.business.dtos.UpdateServiceRequest)
     */
    @Test
    @WithMockUser(username = "g.alex@gmail.com")
    public void updateService_shouldReturnOptionalIdIfTheServiceIsPresent() throws Exception {
        // Arrange
        ServiceRepository serviceRepository = mock(ServiceRepository.class);
        UpdateServiceRequest updateServiceRequest = UpdateServiceRequest.builder()
                .id(1L)
                .description("Second best description!")
                .price(BigDecimal.TEN)
                .build();
        ExpertEntity expert = ExpertEntity.builder()
                .email("g.alex@gmail.com")
                .password("asdASD123@")
                .firstName("Alex")
                .lastName("Gatev")
                .phone("+3161221225")
                .city("Eindhoven")
                .address("Kruisakker 69A")
                .id(1L)
                .build();
        ServiceEntity serviceEntity = ServiceEntity.builder()
                .id(1L)
                .description("Best description ever!")
                .price(BigDecimal.ONE)
                .expert(expert)
                .build();

        when(serviceRepository.findById(1L)).thenReturn(Optional.ofNullable(serviceEntity));
        // Act
        UpdateServiceUseCaseImpl updateServiceUseCase =  new UpdateServiceUseCaseImpl(serviceRepository);
        Optional<Long> actualResponse = updateServiceUseCase.updateService(updateServiceRequest);
        Optional<Long> expectedResponse = Optional.of(1L);
        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(serviceRepository).findById(1L);
    }

    /**
     * @verifies return empty optional if the service id is not present
     * @see UpdateServiceUseCaseImpl#updateService(fontys.sem3.proconnectbackend.business.dtos.UpdateServiceRequest)
     */
    @Test
    public void updateService_shouldReturnEmptyOptionalIfTheServiceIdIsNotPresent() throws Exception {
        // Arrange
        ServiceRepository serviceRepository = mock(ServiceRepository.class);
        UpdateServiceRequest updateServiceRequest = UpdateServiceRequest.builder()
                .id(1L)
                .description("Second best description!")
                .price(BigDecimal.TEN)
                .build();

        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());
        // Act
        UpdateServiceUseCaseImpl updateServiceUseCase =  new UpdateServiceUseCaseImpl(serviceRepository);
        Optional<Long> actualResponse = updateServiceUseCase.updateService(updateServiceRequest);
        Optional<Long> expectedResponse = Optional.empty();
        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(serviceRepository).findById(1L);
    }
}
