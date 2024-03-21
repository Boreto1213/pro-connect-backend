package fontys.sem3.proconnectbackend.business.usecases.service.impl;

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
public class DeleteServiceUseCaseImplTest {
    /**
     * @verifies return optional with the id of the deleted service id if present
     * @see DeleteServiceUseCaseImpl#deleteService(Long)
     */
    @Test
    @WithMockUser(username = "g.alex@gmail.com")
    public void deleteService_shouldReturnOptionalWithTheIdOfTheDeletedServiceIdIfPresent() throws Exception {
        // Arrange
        ServiceRepository serviceRepository = mock(ServiceRepository.class);
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
        Long serviceId = 1L;
        ServiceEntity serviceEntity = ServiceEntity.builder()
                .expert(expert)
                .price(BigDecimal.ONE)
                .description("Best description ever!")
                .id(serviceId)
                .build();

        when(serviceRepository.findById(serviceId)).thenReturn(Optional.ofNullable(serviceEntity));
        // Act
        DeleteServiceUseCaseImpl deleteServiceUseCase = new DeleteServiceUseCaseImpl(serviceRepository);
        Optional<Long> actualResponse = deleteServiceUseCase.deleteService(serviceId);
        Optional<Long> expectedResponse = Optional.of(serviceId);
        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(serviceRepository).findById(serviceId);
    }

    /**
     * @verifies return empty optional empty if the id is not present
     * @see DeleteServiceUseCaseImpl#deleteService(Long)
     */
    @Test
    public void deleteService_shouldReturnEmptyOptionalEmptyIfTheIdIsNotPresent() throws Exception {
        // Arrange
        ServiceRepository serviceRepository = mock(ServiceRepository.class);

        Long serviceId = 1L;

        when(serviceRepository.findById(serviceId)).thenReturn(Optional.empty());
        // Act
        DeleteServiceUseCaseImpl deleteServiceUseCase = new DeleteServiceUseCaseImpl(serviceRepository);
        Optional<Long> actualResponse = deleteServiceUseCase.deleteService(serviceId);
        Optional<Long> expectedResponse = Optional.empty();
        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(serviceRepository).findById(serviceId);
    }
}
