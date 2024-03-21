package fontys.sem3.proconnectbackend.business.usecases.service;

import fontys.sem3.proconnectbackend.business.dtos.UpdateServiceRequest;

import java.util.Optional;

public interface UpdateServiceUseCase {
    Optional<Long> updateService(UpdateServiceRequest request);
}
