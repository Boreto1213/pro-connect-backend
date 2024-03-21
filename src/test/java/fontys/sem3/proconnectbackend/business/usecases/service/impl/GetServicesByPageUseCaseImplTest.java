package fontys.sem3.proconnectbackend.business.usecases.service.impl;

import fontys.sem3.proconnectbackend.business.dtos.GetServicesByPageResponse;
import fontys.sem3.proconnectbackend.domain.Expert;
import fontys.sem3.proconnectbackend.domain.Service;
import fontys.sem3.proconnectbackend.domain.Tag;
import fontys.sem3.proconnectbackend.domain.enums.Role;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import fontys.sem3.proconnectbackend.persistence.entity.TagEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetServicesByPageUseCaseImplTest {
    /**
     * @verifies return the services for the page
     * @see GetServicesByPageUseCaseImpl#getServicesByPage(int)
     */
    @Test
    public void getServicesByPage_shouldReturnTheServicesForThePage() throws Exception {
        // Arrange
        int pageSize = 3;
        int page = 2;
        ServiceRepository serviceRepository = mock(ServiceRepository.class);
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Expert expert = Expert.builder()
                .email("g.alex@gmail.com")
                .password("asdASD123@")
                .firstName("Alex")
                .lastName("Gatev")
                .phone("+3161221225")
                .city("Eindhoven")
                .address("Kruisakker 69A")
                .role(Role.ROLE_Expert)
                .id(1L)
                .build();

        when(serviceRepository.findAll(pageRequest)).thenReturn(createPageOfServices(page, pageSize));
        // Act
        GetServicesByPageUseCaseImpl getServicesByPageUseCase = new GetServicesByPageUseCaseImpl(serviceRepository);
        GetServicesByPageResponse response = getServicesByPageUseCase.getServicesByPage(page);
        GetServicesByPageResponse expectedResponse = GetServicesByPageResponse.builder()
                .services(List.of(Service.builder()
                        .id(1L)
                        .expert(expert)
                        .description("Best description ever!")
                        .price(BigDecimal.valueOf(69.99))
                        .tags(List.of(Tag.builder().id(1L).text("I dont wanna die").build()))
                        .build()))
                .currentPage(2)
                .totalPages(2)
                .build();
        // Assert
        assertEquals(expectedResponse, response);
        assertEquals(2, response.getCurrentPage()); // Check if the current page matches
        assertEquals(1, response.getServices().size());
    }

    public Page<ServiceEntity> createPageOfServices(int page, int pageSize) {
        List<ServiceEntity> serviceList = new ArrayList<>();


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
                .id(1L)
                .expert(expert)
                .description("Best description ever!")
                .price(BigDecimal.valueOf(69.99))
                .tags(List.of(TagEntity.builder().id(1L).text("I dont wanna die").build()))
                .build();
        serviceList.add(service);

        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);

        return new PageImpl<>(serviceList, pageRequest, serviceList.size());
    }
}