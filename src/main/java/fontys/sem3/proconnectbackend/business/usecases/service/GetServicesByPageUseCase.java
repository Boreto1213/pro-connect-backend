package fontys.sem3.proconnectbackend.business.usecases.service;

import fontys.sem3.proconnectbackend.business.dtos.GetServicesByPageResponse;

public interface GetServicesByPageUseCase {
    GetServicesByPageResponse getServicesByPage(int page);
}
