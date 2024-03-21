package fontys.sem3.proconnectbackend.business.usecases.service;

import fontys.sem3.proconnectbackend.domain.Service;

import java.util.Optional;

public interface GetServiceByIdUseCase {
    Optional<Service> getServiceById(Long id);
}

