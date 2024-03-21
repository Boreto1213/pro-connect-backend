package fontys.sem3.proconnectbackend.business.converters;

import fontys.sem3.proconnectbackend.domain.Review;
import fontys.sem3.proconnectbackend.persistence.entity.ReviewEntity;

public class ReviewConverter {
    public static Review convert(ReviewEntity reviewEntity) {
        return Review.builder()
                .id(reviewEntity.getId())
                .service(ServiceConverter.convert(reviewEntity.getService()))
                .createdBy(ClientConverter.convert(reviewEntity.getCreatedBy()))
                .text(reviewEntity.getText())
                .rating(reviewEntity.getRating())
                .createdAt(reviewEntity.getCreatedAt())
                .build();
    }
}
