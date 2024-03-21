package fontys.sem3.proconnectbackend.business.usecases.review;

import java.util.Optional;

public interface DeleteReviewUseCase {
    Optional<Long> deleteReview(Long id);
}
