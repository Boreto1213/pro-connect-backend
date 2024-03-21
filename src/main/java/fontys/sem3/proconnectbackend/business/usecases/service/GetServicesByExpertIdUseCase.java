package fontys.sem3.proconnectbackend.business.usecases.service;

import fontys.sem3.proconnectbackend.domain.Service;

import java.util.ArrayList;

public interface GetServicesByExpertIdUseCase {
    ArrayList<Service> getServicesByExpertId(Long id);
}
