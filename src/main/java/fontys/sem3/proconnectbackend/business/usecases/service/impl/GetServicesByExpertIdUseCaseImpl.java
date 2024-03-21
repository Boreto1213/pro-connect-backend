package fontys.sem3.proconnectbackend.business.usecases.service.impl;

import fontys.sem3.proconnectbackend.business.converters.ServiceConverter;
import fontys.sem3.proconnectbackend.business.usecases.service.GetServicesByExpertIdUseCase;
import fontys.sem3.proconnectbackend.domain.Service;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class GetServicesByExpertIdUseCaseImpl implements GetServicesByExpertIdUseCase {
    private ServiceRepository serviceRepository;

    /**
     *
     * @param id the id of the expert whose services are seeked
     * @should return ArrayList with the services of the expert
     * @should return Empty arrayList when no services are found
     */
    @Override
    public ArrayList<Service> getServicesByExpertId(Long id) {
        return serviceRepository.findServiceEntitiesByExpert_Id(id)
                .stream()
                .map(ServiceConverter::convert)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
