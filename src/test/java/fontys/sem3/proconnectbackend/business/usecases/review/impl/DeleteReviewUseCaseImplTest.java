package fontys.sem3.proconnectbackend.business.usecases.review.impl;

import fontys.sem3.proconnectbackend.persistence.ReviewRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ReviewEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteReviewUseCaseImplTest {
    /**
     * @verifies return the id of the deleted review if it exists
     * @see DeleteReviewUseCaseImpl#deleteReview(Long)
     */
    @Test
    @WithMockUser("asd@gmail.com")
    public void deleteReview_shouldReturnTheIdOfTheDeletedReviewIfItExists() throws Exception {
        // Arrange
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
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
                .build();
        ReviewEntity reviewEntity = ReviewEntity.builder()
                .id(1L)
                .text("Worst service ever!")
                .createdAt(new Date())
                .createdBy(clientEntity)
                .service(serviceEntity)
                .rating(BigDecimal.ONE)
                .build();

        when(reviewRepository.findById(1L)).thenReturn(Optional.ofNullable(reviewEntity));
        // Act
        DeleteReviewUseCaseImpl deleteReviewUseCase = new DeleteReviewUseCaseImpl(reviewRepository);
        Optional<Long> actualResponse = deleteReviewUseCase.deleteReview(1L);
        Optional<Long> expectedResponse = Optional.of(1L);
        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(reviewRepository).findById(1L);
    }

    /**
     * @verifies return empty optional if the id does not exist
     * @see DeleteReviewUseCaseImpl#deleteReview(Long)
     */
    @Test
    public void deleteReview_shouldReturnEmptyOptionalIfTheIdDoesNotExist() throws Exception {
        // Arrange
        ReviewRepository reviewRepository = mock(ReviewRepository.class);


        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());
        // Act
        DeleteReviewUseCaseImpl deleteReviewUseCase = new DeleteReviewUseCaseImpl(reviewRepository);
        Optional<Long> actualResponse = deleteReviewUseCase.deleteReview(1L);
        Optional<Long> expectedResponse = Optional.empty();
        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(reviewRepository).findById(1L);
    }
}
