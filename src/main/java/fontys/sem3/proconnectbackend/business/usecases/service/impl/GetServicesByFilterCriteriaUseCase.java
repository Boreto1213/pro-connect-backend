package fontys.sem3.proconnectbackend.business.usecases.service.impl;

import fontys.sem3.proconnectbackend.business.converters.ServiceConverter;
import fontys.sem3.proconnectbackend.business.dtos.GetServicesByPageResponse;
import fontys.sem3.proconnectbackend.business.usecases.service.GetServicesByFilterCriteria;
import fontys.sem3.proconnectbackend.domain.Service;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class GetServicesByFilterCriteriaUseCase implements GetServicesByFilterCriteria {
    private final ServiceRepository serviceRepository;

//    @Value("${service.page.size}")
    private int pageSize = 3;


    /**
     *
     * @param titleQuery the query of the title
     * @param minPrice the min price for a service
     * @param maxPrice the max price for a service
     * @param page the page number
     * @should return the services for the page
     */
    @Override
    public GetServicesByPageResponse getServicesByQueryTitleAndPriceRangeAndPage(String titleQuery, BigDecimal minPrice, BigDecimal maxPrice, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<Service> services = serviceRepository
                .findServiceEntitiesByTitleContainingAndPriceBetween(titleQuery, minPrice, maxPrice, pageRequest)
                .map(ServiceConverter::convert);
        return GetServicesByPageResponse.builder()
                .services(services.getContent())
                .totalPages(services.getTotalPages())
                .currentPage(page)
                .build();
    }
}
