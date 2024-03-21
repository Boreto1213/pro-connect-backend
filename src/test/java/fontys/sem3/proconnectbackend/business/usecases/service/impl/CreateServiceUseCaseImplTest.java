package fontys.sem3.proconnectbackend.business.usecases.service.impl;

import fontys.sem3.proconnectbackend.business.dtos.CreateServiceRequest;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidUserIdException;
import fontys.sem3.proconnectbackend.domain.Tag;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import fontys.sem3.proconnectbackend.persistence.ServiceTagsRepository;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CreateServiceUseCaseImplTest {
    /**
     * @verifies return Optional<Long> when the creation is successful
     * @see CreateServiceUseCaseImpl#createService(fontys.sem3.proconnectbackend.business.dtos.CreateServiceRequest)
     */
    @Test
    public void CreateService_shouldReturnOptionalLongWhenTheCreationIsSuccessful() throws Exception {
        // Arrange
        ServiceRepository serviceRepository = mock(ServiceRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        ServiceTagsRepository serviceTagsRepository = mock(ServiceTagsRepository.class);
        ArrayList<Tag> tags = new ArrayList<>(List.of(Tag.builder().id(1L).text("IT").build(), Tag.builder().id(2L).text("IT").build()));
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
        CreateServiceRequest request = CreateServiceRequest.builder()
                .expertId(1L)
                .description("Best description ever!")
                .price(BigDecimal.valueOf(69.99))
                .tags(tags)
                .build();
        ServiceEntity service = ServiceEntity.builder()
                .expert(expert)
                .description("Best description ever!")
                .price(BigDecimal.valueOf(69.99))
                .build();
        ServiceEntity savedService = ServiceEntity.builder()
                .id(1L)
                .expert(expert)
                .description("Best description ever!")
                .price(BigDecimal.valueOf(69.99))
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(expert));
        when(serviceRepository.save(service)).thenReturn(savedService);
        // Act
        CreateServiceUseCaseImpl createServiceUseCase = new CreateServiceUseCaseImpl(serviceRepository, userRepository, serviceTagsRepository);
        createServiceUseCase.createService(request);
        // Assert
        Mockito.verify(serviceRepository, Mockito.times(1)).save(service);
        verify(serviceRepository).save(service);
    }

    /**
     * @verifies return Optional<Empty> when the creation is unsuccessful
     * @see CreateServiceUseCaseImpl#createService(fontys.sem3.proconnectbackend.business.dtos.CreateServiceRequest)
     */
    @Test
    public void CreateService_shouldReturnOptionalEmptyWhenTheCreationIsUnsuccessful() throws Exception {
        ServiceRepository serviceRepository = mock(ServiceRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        ServiceTagsRepository serviceTagsRepository = mock(ServiceTagsRepository.class);
        // Arrange

        CreateServiceRequest request = CreateServiceRequest.builder()
                .expertId(1L)
                .description("Best description ever!")
                .price(BigDecimal.valueOf(69.99))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        // Act
        CreateServiceUseCaseImpl createServiceUseCase = new CreateServiceUseCaseImpl(serviceRepository, userRepository, serviceTagsRepository);
        // Assert
        assertThrows(InvalidUserIdException.class, () -> createServiceUseCase.createService(request));
        Mockito.verify(serviceRepository, Mockito.times(0)).save(new ServiceEntity());

    }
}
