package fontys.sem3.proconnectbackend.business.usecases.service.impl;

import fontys.sem3.proconnectbackend.business.dtos.UpdateServiceRequest;
import fontys.sem3.proconnectbackend.business.exeptions.DataOwnershipViolationException;
import fontys.sem3.proconnectbackend.business.usecases.service.UpdateServiceUseCase;
import fontys.sem3.proconnectbackend.configuration.security.DataOwnershipValidator;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateServiceUseCaseImpl implements UpdateServiceUseCase {
    ServiceRepository serviceRepository;

    /**
     *
     * @param request contans all the required data for updating a service
     * @should return Optional id if the service is present
     * @should return empty optional if the service id is not present
     */
    @Override
    @Transactional
    public Optional<Long> updateService(UpdateServiceRequest request) throws DataOwnershipViolationException {
        Optional<ServiceEntity> serviceEntityOptional = serviceRepository.findById(request.getId());

        if (serviceEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        if (!DataOwnershipValidator.canUserModifyServiceResource(serviceEntityOptional.get())) {
            throw new DataOwnershipViolationException();
        }

        ServiceEntity serviceEntity = serviceEntityOptional.get();
        serviceEntity.setDescription(request.getDescription());
        serviceEntity.setPrice(request.getPrice());
        serviceRepository.save(serviceEntity);

        return Optional.of(request.getId());
    }
}
