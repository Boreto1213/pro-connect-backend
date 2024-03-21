package fontys.sem3.proconnectbackend.business.usecases.review;

import fontys.sem3.proconnectbackend.domain.Review;

import java.util.ArrayList;

public interface GetReviewsByServiceIdUseCase {
    ArrayList<Review> getReviewsByServiceId(Long id);
}
