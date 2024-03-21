package fontys.sem3.proconnectbackend.business.usecases.service.impl;

import fontys.sem3.proconnectbackend.business.converters.ServiceConverter;
import fontys.sem3.proconnectbackend.business.usecases.service.GetServiceByIdUseCase;
import fontys.sem3.proconnectbackend.domain.Service;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import lombok.AllArgsConstructor;

import java.util.Optional;


@org.springframework.stereotype.Service
@AllArgsConstructor
public class GetServiceByIdUseCaseImpl implements GetServiceByIdUseCase {
    ServiceRepository serviceRepository;

    /**
     *
     * @param id the id of the seeked service
     * @should return the service object in a optional if the id is present
     * @should return empty optional if the id is not present
     */
    @Override
    public Optional<Service> getServiceById(Long id) {
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(id);

        return serviceEntity.map(ServiceConverter::convert);

    }
}
