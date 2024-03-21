package fontys.sem3.proconnectbackend.business.usecases.review.impl;

import fontys.sem3.proconnectbackend.business.converters.ReviewConverter;
import fontys.sem3.proconnectbackend.business.exeptions.ServiceIdDoesNotExistException;
import fontys.sem3.proconnectbackend.domain.Review;
import fontys.sem3.proconnectbackend.persistence.ReviewRepository;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import fontys.sem3.proconnectbackend.persistence.entity.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetReviewsByServiceIdUseCaseImplTest {
    /**
     * @verifies return ArrayList with the reviews for that service
     * @see GetReviewsByServiceIdUseCaseImpl#getReviewsByServiceId(Long)
     */
    @Test
    public void getReviewsByServiceId_shouldReturnArrayListWithTheReviewsForThatService() throws Exception {
        // Arrange
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        ServiceRepository serviceRepository = mock(ServiceRepository.class);
        ExpertEntity expert = ExpertEntity.builder()
                .email("g.alex@gmail.com")
                .password("asdASD123@")
                .firstName("Alex")
                .lastName("Gatev")
                .phone("+3161221225")
                .city("Eindhoven")
                .address("Kruisakker 69A")
                .id(1L)
                .build();
        ServiceEntity serviceEntity = ServiceEntity.builder()
                .id(1L)
                .expert(expert)
                .description("Best description ever!")
                .price(BigDecimal.valueOf(69.99))
                .tags(List.of(TagEntity.builder().id(1L).text("I wanna die").build()))
                .build();
        ClientEntity clientEntity = ClientEntity.builder()
                .email("asd@gmail.com")
                .password("asdASD123@")
                .firstName("Alex")
                .lastName("Gatev")
                .phone("+3161221225")
                .city("Eindhoven")
                .address("Kruisakker 69A")
                .id(2L)
                .build();
        ReviewEntity reviewEntity = ReviewEntity.builder()
                .id(1L)
                .createdBy(clientEntity)
                .rating(BigDecimal.ONE)
                .createdAt(new Date())
                .service(serviceEntity)
                .text("Best service ever!")
                .build();
        ArrayList<ReviewEntity> queryResult = new ArrayList<>();
        queryResult.add(reviewEntity);
        when(serviceRepository.findById(1L)).thenReturn(Optional.ofNullable(serviceEntity));
        when(reviewRepository.findReviewEntitiesByService(serviceEntity)).thenReturn(queryResult);
        // Act
        GetReviewsByServiceIdUseCaseImpl getReviewsByServiceIdUseCase = new GetReviewsByServiceIdUseCaseImpl(reviewRepository, serviceRepository);
        ArrayList<Review> actualResponse = getReviewsByServiceIdUseCase.getReviewsByServiceId(serviceEntity.getId());
        ArrayList<Review> expectedResponse = new ArrayList<Review>() {{
            add(ReviewConverter.convert(reviewEntity));
        }};
        // Arrange
        assertEquals(expectedResponse, actualResponse);
        verify(reviewRepository).findReviewEntitiesByService(serviceEntity);
    }

    /**
     * @verifies throw ServiceIdDoesNotExistException if the id of the service does not exist
     * @see GetReviewsByServiceIdUseCaseImpl#getReviewsByServiceId(Long)
     */
    @Test
    public void getReviewsByServiceId_shouldThrowServiceIdDoesNotExistExceptionIfTheIdOfTheServiceDoesNotExist() throws Exception {
        // Arrange
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        ServiceRepository serviceRepository = mock(ServiceRepository.class);
        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());
        // Act
        GetReviewsByServiceIdUseCaseImpl getReviewsByServiceIdUseCase = new GetReviewsByServiceIdUseCaseImpl(reviewRepository, serviceRepository);

        // Arrange
        assertThrows(ServiceIdDoesNotExistException.class, () -> getReviewsByServiceIdUseCase.getReviewsByServiceId(1L));
    }
}
