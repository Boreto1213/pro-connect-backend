package fontys.sem3.proconnectbackend.business.usecases.service.impl;

import fontys.sem3.proconnectbackend.business.converters.ServiceConverter;
import fontys.sem3.proconnectbackend.domain.Service;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import fontys.sem3.proconnectbackend.persistence.entity.TagEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetServiceByIdUseCaseImplTest {
    /**
     * @verifies return the service object in a optional if the id is present
     * @see GetServiceByIdUseCaseImpl#getServiceById(Long)
     */
    @Test
    public void getServiceById_shouldReturnTheServiceObjectInAOptionalIfTheIdIsPresent() throws Exception {
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
        ServiceEntity serviceEntity = ServiceEntity.builder()
                .id(1L)
                .description("Best description ever!")
                .price(BigDecimal.ONE)
                .expert(expert)
                .tags(List.of(TagEntity.builder().id(1L).text("I dont wanna die").build()))
                .build();

        when(serviceRepository.findById(1L)).thenReturn(Optional.ofNullable(serviceEntity));
        // Act
        GetServiceByIdUseCaseImpl getServiceByIdUseCase = new GetServiceByIdUseCaseImpl(serviceRepository);
        Optional<Service> actualResponse = getServiceByIdUseCase.getServiceById(1L);
        Optional<Service> expectedResponse = Optional.of(ServiceConverter.convert(serviceEntity));
        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(serviceRepository).findById(1L);
    }

    /**
     * @verifies return empty optional if the id is not present
     * @see GetServiceByIdUseCaseImpl#getServiceById(Long)
     */
    @Test
    public void getServiceById_shouldReturnEmptyOptionalIfTheIdIsNotPresent() throws Exception {
        // Arrange
        ServiceRepository serviceRepository = mock(ServiceRepository.class);


        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());
        // Act
        GetServiceByIdUseCaseImpl getServiceByIdUseCase = new GetServiceByIdUseCaseImpl(serviceRepository);
        Optional<Service> actualResponse = getServiceByIdUseCase.getServiceById(1L);
        Optional<Service> expectedResponse = Optional.empty();
        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(serviceRepository).findById(1L);
    }
}
