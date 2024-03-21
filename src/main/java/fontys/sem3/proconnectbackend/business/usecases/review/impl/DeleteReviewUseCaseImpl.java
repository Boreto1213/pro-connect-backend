package fontys.sem3.proconnectbackend.business.usecases.review.impl;

import fontys.sem3.proconnectbackend.business.exeptions.DataOwnershipViolationException;
import fontys.sem3.proconnectbackend.business.usecases.review.DeleteReviewUseCase;
import fontys.sem3.proconnectbackend.configuration.security.DataOwnershipValidator;
import fontys.sem3.proconnectbackend.persistence.ReviewRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ReviewEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteReviewUseCaseImpl implements DeleteReviewUseCase {
    private ReviewRepository reviewRepository;

    /**
     *
     * @param id the id of the review to be deleted
     * @should return the id of the deleted review if it exists
     * @should return empty optional if the id does not exist
     */
    @Override
    @Transactional
    public Optional<Long> deleteReview(@NotNull Long id) throws DataOwnershipViolationException {
        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(id);

        if (reviewEntity.isEmpty()) {
            return Optional.empty();
        }

        if (!DataOwnershipValidator.canUserModifyReviewResource(reviewEntity.get())) {
            throw new DataOwnershipViolationException();
        }

        reviewRepository.delete(reviewEntity.get());

        return Optional.of(id);
    }
}
