package fontys.sem3.proconnectbackend.business.usecases.review.impl;

import fontys.sem3.proconnectbackend.business.dtos.CreateReviewRequest;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidRequestDataException;
import fontys.sem3.proconnectbackend.persistence.ReviewRepository;
import fontys.sem3.proconnectbackend.persistence.ServiceRepository;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ReviewEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CreateReviewUseCaseImplTest {
    /**
     * @verifies throw exception when request data is not valid
     * @see CreateReviewUseCaseImpl#createReview(fontys.sem3.proconnectbackend.business.dtos.CreateReviewRequest)
     */
    @Test
    public void createReview_shouldThrowExceptionWhenRequestDataIsNotValid() throws Exception {
        // Arrange
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        ServiceRepository serviceRepository = mock(ServiceRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

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
        ServiceEntity service = ServiceEntity.builder()
                .id(1L)
                .expert(expert)
                .description("Best description ever!")
                .price(BigDecimal.valueOf(69.99))
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
        Date date = new Date();
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .text("Best service ever!")
                .clientId(2L)
                .createdAt(date)
                .rating(BigDecimal.ONE)
                .serviceId(1L)
                .build();
        ReviewEntity reviewEntity = ReviewEntity.builder()
                .createdBy(clientEntity)
                .rating(BigDecimal.ONE)
                .createdAt(date)
                .service(service)
                .text("Best service ever!")
                .build();
        when(serviceRepository.findById(1L)).thenReturn(Optional.ofNullable(service));
        when(userRepository.findById(2L)).thenReturn(Optional.ofNullable(clientEntity));
        // Act
        CreateReviewUseCaseImpl createReviewUseCase = new CreateReviewUseCaseImpl(reviewRepository, serviceRepository, userRepository);
        createReviewUseCase.createReview(createReviewRequest);
        // Assert
        Mockito.verify(reviewRepository, Mockito.times(1)).save(reviewEntity);
        verify(reviewRepository).save(reviewEntity);
    }

    /**
     * @verifies save the review if the request data is valid
     * @see CreateReviewUseCaseImpl#createReview(fontys.sem3.proconnectbackend.business.dtos.CreateReviewRequest)
     */
    @Test
    public void createReview_shouldSaveTheReviewIfTheRequestDataIsValid() throws Exception {
        // Arrange
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        ServiceRepository serviceRepository = mock(ServiceRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        Date date = new Date();
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .text("Best service ever!")
                .clientId(2L)
                .createdAt(date)
                .rating(BigDecimal.ONE)
                .serviceId(1L)
                .build();

        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        // Act
        CreateReviewUseCaseImpl createReviewUseCase = new CreateReviewUseCaseImpl(reviewRepository, serviceRepository, userRepository);
        // Assert
        assertThrows(InvalidRequestDataException.class, () -> createReviewUseCase.createReview(createReviewRequest));
        Mockito.verify(reviewRepository, Mockito.times(0)).save(new ReviewEntity());
    }
}
