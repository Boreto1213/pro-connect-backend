package fontys.sem3.proconnectbackend.business.usecases.review;

import fontys.sem3.proconnectbackend.business.dtos.CreateReviewRequest;

public interface CreateReviewUseCase {
    public void createReview(CreateReviewRequest request);
}
