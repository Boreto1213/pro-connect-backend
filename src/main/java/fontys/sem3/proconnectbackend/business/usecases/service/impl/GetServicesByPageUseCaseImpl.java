package fontys.sem3.proconnectbackend.business.usecases.service.impl;

import fontys.sem3.proconnectbackend.business.converters.ServiceConverter;
import fontys.sem3.proconnectbackend.business.dtos.GetServicesByPageResponse;
import fontys.sem3.proconnectbackend.business.usecases.service.GetServicesByPageUseCase;
import fontys.sem3.proconnectbackend.domain.Service;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class GetServicesByPageUseCaseImpl implements GetServicesByPageUseCase {
    private final ServiceRepository serviceRepository;

    private final int pageSize = 3;

    /**
     *
     * @param page the page number
     * @should return the services for the page
     */
    @Override
    public GetServicesByPageResponse getServicesByPage(int page) {
        PageRequest pageRequest = PageRequest.of( page - 1, pageSize);
        Page<Service> services = serviceRepository.findAll(pageRequest).map(ServiceConverter::convert);

        return GetServicesByPageResponse.builder()
                .totalPages(services.getTotalPages())
                .currentPage(page)
                .services(services.getContent())
                .build();
    }
}
