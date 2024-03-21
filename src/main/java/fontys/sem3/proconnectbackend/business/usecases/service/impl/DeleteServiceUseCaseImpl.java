package fontys.sem3.proconnectbackend.business.usecases.service.impl;

import fontys.sem3.proconnectbackend.business.exeptions.DataOwnershipViolationException;
import fontys.sem3.proconnectbackend.business.usecases.service.DeleteServiceUseCase;
import fontys.sem3.proconnectbackend.configuration.security.DataOwnershipValidator;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteServiceUseCaseImpl implements DeleteServiceUseCase {
    ServiceRepository serviceRepository;

    /**
     *
     * @param id the id of the service to be deleted
     * @should return optional with the id of the deleted service id if present
     * @should return empty optional empty if the id is not present
     */
    @Override
    @Transactional
    public Optional<Long> deleteService(@NotNull Long id) throws DataOwnershipViolationException {
        Optional<ServiceEntity> serviceEntityOptional = serviceRepository.findById(id);
        if (serviceEntityOptional.isPresent()) {
            if (!DataOwnershipValidator.canUserModifyServiceResource(serviceEntityOptional.get())) {
                throw new DataOwnershipViolationException();
            }
            serviceRepository.delete(serviceEntityOptional.get());
            return Optional.of(id);
        }
        return Optional.empty();
    }
}
