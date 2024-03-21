package fontys.sem3.proconnectbackend.business.usecases.service.impl;

import fontys.sem3.proconnectbackend.business.converters.ServiceConverter;
import fontys.sem3.proconnectbackend.business.usecases.service.GetServicesByExpertIdUseCase;
import fontys.sem3.proconnectbackend.domain.Service;
import fontys.sem3.proconnectbackend.domain.Tag;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import fontys.sem3.proconnectbackend.persistence.entity.TagEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetServicesByExpertIdUseCaseImplTest {
    /**
     * @verifies return ArrayList with the services of the expert
     * @see GetServicesByExpertIdUseCaseImpl#getServicesByExpertId(Long)
     */
    @Test
    public void getServicesByExpertId_shouldReturnArrayListWithTheServicesOfTheExpert() throws Exception {
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
        ServiceEntity service = ServiceEntity.builder()
                .expert(expert)
                .description("Best description ever!")
                .price(BigDecimal.valueOf(69.99))
                .tags(List.of(TagEntity.builder().id(1L).text("I dont wanna die").build()))
                .build();
        ArrayList<ServiceEntity> queryResult = new ArrayList<>();
        queryResult.add(service);

        when(serviceRepository.findServiceEntitiesByExpert_Id(expert.getId())).thenReturn(queryResult);
        // Act
        GetServicesByExpertIdUseCase getServicesByExpertIdUseCase = new GetServicesByExpertIdUseCaseImpl(serviceRepository);
        ArrayList<Service> actualResponse = getServicesByExpertIdUseCase.getServicesByExpertId(expert.getId());
        ArrayList<Service> expectedResponse = new ArrayList<Service>() {{
            add(ServiceConverter.convert(service));
        }};
        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(serviceRepository).findServiceEntitiesByExpert_Id(expert.getId());
    }

    /**
     * @verifies return Empty arrayList when no services are found
     * @see GetServicesByExpertIdUseCaseImpl#getServicesByExpertId(Long)
     */
    @Test
    public void getServicesByExpertId_shouldReturnEmptyArrayListWhenNoServicesAreFound() throws Exception {
        // Arrange
        ServiceRepository serviceRepository = mock(ServiceRepository.class);

        when(serviceRepository.findServiceEntitiesByExpert_Id(1L)).thenReturn(new ArrayList<ServiceEntity>());
        // Act
        GetServicesByExpertIdUseCase getServicesByExpertIdUseCase = new GetServicesByExpertIdUseCaseImpl(serviceRepository);
        ArrayList<Service> actualResponse = getServicesByExpertIdUseCase.getServicesByExpertId(1L);
        ArrayList<Service> expectedResponse = new ArrayList<Service>();
        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(serviceRepository).findServiceEntitiesByExpert_Id(1L);
    }
}
