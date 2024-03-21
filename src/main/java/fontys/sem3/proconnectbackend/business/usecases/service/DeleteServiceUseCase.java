package fontys.sem3.proconnectbackend.business.usecases.service;

import java.util.Optional;

public interface DeleteServiceUseCase {
    Optional<Long> deleteService(Long id);
}
