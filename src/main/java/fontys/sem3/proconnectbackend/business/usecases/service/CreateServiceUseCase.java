package fontys.sem3.proconnectbackend.business.usecases.service;

import fontys.sem3.proconnectbackend.business.dtos.CreateServiceRequest;

public interface CreateServiceUseCase {
    void createService(CreateServiceRequest request);
}
