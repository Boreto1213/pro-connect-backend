package fontys.sem3.proconnectbackend.business.usecases.service.impl;

import fontys.sem3.proconnectbackend.business.dtos.CreateServiceRequest;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidUserIdException;
import fontys.sem3.proconnectbackend.business.usecases.service.CreateServiceUseCase;
import fontys.sem3.proconnectbackend.domain.Tag;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import fontys.sem3.proconnectbackend.persistence.ServiceTagsRepository;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import fontys.sem3.proconnectbackend.persistence.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateServiceUseCaseImpl implements CreateServiceUseCase {
    final ServiceRepository serviceRepository;
    final UserRepository userRepository;
    final ServiceTagsRepository serviceTagsRepository;

    /**
     * @param request object containing all required data to create a service
     * @should return Optional<Long> when the creation is successful
     * @should return Optional<Empty> when the creation is unsuccessful
     */
    @Override
    @Transactional
    public void createService(@Valid CreateServiceRequest request) {
        Optional<UserEntity> expertEntityOptional = userRepository.findById(request.getExpertId());

        if (expertEntityOptional.isEmpty() || !(expertEntityOptional.get() instanceof ExpertEntity expertEntity)) {
            throw new InvalidUserIdException();
        }

        ServiceEntity serviceEntity = ServiceEntity.builder()
                .expert(expertEntity)
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        ServiceEntity savedServiceEntity = serviceRepository.save(serviceEntity);

        for (Tag tag : request.getTags()) {
            serviceTagsRepository.addTagToService(savedServiceEntity.getId(), tag.getId());
        }
    }
}
