package fontys.sem3.proconnectbackend.business.usecases.review.impl;

import fontys.sem3.proconnectbackend.business.dtos.CreateReviewRequest;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidRequestDataException;
import fontys.sem3.proconnectbackend.business.usecases.review.CreateReviewUseCase;
import fontys.sem3.proconnectbackend.persistence.ReviewRepository;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ReviewEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import fontys.sem3.proconnectbackend.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateReviewUseCaseImpl implements CreateReviewUseCase {
    private ReviewRepository reviewRepository;
    private ServiceRepository serviceRepository;
    private UserRepository userRepository;

    /**
     *
     * @param request contains all required data to create a review
     * @should throw exception when request data is not valid
     * @should save the review if the request data is valid
     */
    @Override
    @Transactional
    public void createReview(CreateReviewRequest request) {
        Optional<UserEntity> userEntity = userRepository.findById(request.getClientId());
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(request.getServiceId());

        if (userEntity.isEmpty() || serviceEntity.isEmpty() || !(userEntity.get() instanceof ClientEntity)) {
            throw new InvalidRequestDataException();
        }

        ReviewEntity review = ReviewEntity.builder()
                .text(request.getText())
                .createdBy((ClientEntity) userEntity.get())
                .createdAt(request.getCreatedAt())
                .rating(request.getRating())
                .service(serviceEntity.get())
                .build();

        reviewRepository.save(review);
    }
}