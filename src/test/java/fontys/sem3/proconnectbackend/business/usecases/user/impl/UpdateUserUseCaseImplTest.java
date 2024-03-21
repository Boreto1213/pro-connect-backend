package fontys.sem3.proconnectbackend.business.usecases.user.impl;

import fontys.sem3.proconnectbackend.business.dtos.UpdateUserRequest;
import fontys.sem3.proconnectbackend.business.exeptions.DataOwnershipViolationException;
import fontys.sem3.proconnectbackend.business.exeptions.EmailAlreadyExistsException;
import fontys.sem3.proconnectbackend.business.usecases.user.impl.UpdateUserUseCaseImpl;
import fontys.sem3.proconnectbackend.configuration.AzureBlobService;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UpdateUserUseCaseImplTest {
    /**
     * @verifies return Optional<userId> when the userId exists
     * @see UpdateUserUseCaseImpl#updateUser(UpdateUserRequest)
     */
    @Test
    @WithMockUser(username = "g.alex@gmail.com")
    public void updateUser_shouldReturnOptionalUserIdWhenTheUserIdExists() throws Exception {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AzureBlobService azureBlobService = mock(AzureBlobService.class);
        Long existingUserId = 1L;
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .id(existingUserId)
                .email("g.alex@gmail.com")
                .firstName("Alex")
                .lastName("Getov")
                .phone("+31692212534")
                .address("Kruisakker 60B")
                .city("Amsterdam")
                .profession("Debt Collector")
                .build();

        ExpertEntity expertEntity = ExpertEntity.builder()
                .id(existingUserId)
                .email("g.alex@gmail.com")
                .password("asdASD123@")
                .firstName("Alex")
                .lastName("Getov")
                .phone("+31692212534")
                .address("Kruisakker 60B")
                .city("Eindhoven")
                .build();

        when(userRepository.findById(existingUserId)).thenReturn(Optional.ofNullable(expertEntity));
        // Act
        UpdateUserUseCaseImpl updateUserUseCaseImpl = new UpdateUserUseCaseImpl(userRepository, passwordEncoder, azureBlobService);
        Optional<Long> actualResponse = updateUserUseCaseImpl.updateUser(updateUserRequest);
        Optional<Long> expectedResponse = Optional.ofNullable(existingUserId);
        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(userRepository).findById(existingUserId);
    }

    /**
     * @verifies return Optional<null> when the userId exists
     * @see UpdateUserUseCaseImpl#updateUser(UpdateUserRequest)
     */
    @Test
    @WithMockUser(username = "g.alex@gmail.com")
    public void updateUser_shouldReturnOptionalNullWhenTheUserIdDoesNotExist() throws Exception {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AzureBlobService azureBlobService = mock(AzureBlobService.class);

        Long nonExistingId = 1L;
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .id(nonExistingId)
                .email("g.alex@gmail.com")
                .firstName("Alex")
                .lastName("Getov")
                .phone("+31692212534")
                .address("Kruisakker 60B")
                .city("Amsterdam")
                .profession("Debt Collector")
                .build();


        when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        // Act
        UpdateUserUseCaseImpl updateUserUseCaseImpl = new UpdateUserUseCaseImpl(userRepository, passwordEncoder, azureBlobService);
        Optional<Long> actualResponse = updateUserUseCaseImpl.updateUser(updateUserRequest);
        Optional<Long> expectedResponse = Optional.empty();
        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(userRepository).findById(nonExistingId);
    }

    @Test
    @WithMockUser(username = "not.exitent.user@gmail.com")
    public void updateUser_shouldThrowDataOwnershipViolationExceptionWhenLoggedUserIsNotTheSame() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AzureBlobService azureBlobService = mock(AzureBlobService.class);
        Long existingUserId = 1L;
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .id(existingUserId)
                .email("g.alex@gmail.com")
                .firstName("Alex")
                .lastName("Getov")
                .phone("+31692212534")
                .address("Kruisakker 60B")
                .city("Amsterdam")
                .profession("Debt Collector")
                .build();

        ExpertEntity expertEntity = ExpertEntity.builder()
                .id(existingUserId)
                .email("g.alex@gmail.com")
                .password("asdASD123@")
                .firstName("Alex")
                .lastName("Getov")
                .phone("+31692212534")
                .address("Kruisakker 60B")
                .city("Eindhoven")
                .build();

        when(userRepository.findById(existingUserId)).thenReturn(Optional.ofNullable(expertEntity));
        UpdateUserUseCaseImpl updateUserUseCaseImpl = new UpdateUserUseCaseImpl(userRepository, passwordEncoder, azureBlobService);

        // Assert
        assertThrows(DataOwnershipViolationException.class, () -> updateUserUseCaseImpl.updateUser(updateUserRequest));
    }
}
