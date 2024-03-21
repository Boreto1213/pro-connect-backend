package fontys.sem3.proconnectbackend.business.usecases.review.impl;

import fontys.sem3.proconnectbackend.business.converters.ReviewConverter;
import fontys.sem3.proconnectbackend.business.exeptions.ServiceIdDoesNotExistException;
import fontys.sem3.proconnectbackend.business.usecases.review.GetReviewsByServiceIdUseCase;
import fontys.sem3.proconnectbackend.domain.Review;
import fontys.sem3.proconnectbackend.persistence.ReviewRepository;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class GetReviewsByServiceIdUseCaseImpl implements GetReviewsByServiceIdUseCase {
    private ReviewRepository reviewRepository;
    private ServiceRepository serviceRepository;

    /**
     *
     * @param id the id of the service which reviews are requested
     * @should return ArrayList with the reviews for that service
     * @should throw ServiceIdDoesNotExistException if the id of the service does not exist
     */
    @Override
    @Transactional
    public ArrayList<Review> getReviewsByServiceId(Long id) {
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(id);

        if (serviceEntity.isEmpty()) {
            throw new ServiceIdDoesNotExistException();
        }

        return reviewRepository.findReviewEntitiesByService(serviceEntity.get())
                .stream()
                .map(ReviewConverter::convert)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
