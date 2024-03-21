package fontys.sem3.proconnectbackend.business.usecases.user.impl;

import fontys.sem3.proconnectbackend.business.exeptions.DataOwnershipViolationException;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidUserIdException;
import fontys.sem3.proconnectbackend.configuration.AzureBlobService;
import fontys.sem3.proconnectbackend.configuration.security.DataOwnershipValidator;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UpdateUserProfileImageUseCaseImplTest {
    /**
     * @verifies execute when invoked with correct data
     * @see UpdateUserProfileImageUseCaseImpl#updateProfileImage(Long, org.springframework.web.multipart.MultipartFile)
     */
    @Test
    @WithMockUser("a.getov@gmail.com")
    public void updateProfileImage_shouldExecuteWhenInvokedWithCorrectData() throws Exception {
        // Arrange
        Long userId = 1L;
        UserEntity mockUser = new UserEntity(); // Assuming UserEntity is your user entity class
        mockUser.setId(userId);
        mockUser.setEmail("a.getov@gmail.com");
        MultipartFile mockImage = mock(MultipartFile.class);
        String mockUrl = "http://example.com/profile.jpg";
        AzureBlobService azureBlobService = mock(AzureBlobService.class);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(azureBlobService.upload(mockImage)).thenReturn(mockUrl);

        // Act
        UpdateUserProfileImageUseCaseImpl updateUserProfileImageUseCase = new UpdateUserProfileImageUseCaseImpl(azureBlobService, userRepository);
        updateUserProfileImageUseCase.updateProfileImage(userId, mockImage);

        // Assert
        verify(userRepository).save(mockUser);
        assertEquals(mockUrl, mockUser.getProfileImageUrl());
    }

    /**
     * @verifies throw InvalidUserIdException when the userId does not exist
     * @see UpdateUserProfileImageUseCaseImpl#updateProfileImage(Long, org.springframework.web.multipart.MultipartFile)
     */
    @Test
    @WithMockUser("a.getov@gmail.com")
    public void updateProfileImage_shouldThrowInvalidUserIdExceptionWhenTheUserIdDoesNotExist() throws Exception {
        // Arrange
        Long userId = 1L;
        UserEntity mockUser = new UserEntity(); // Assuming UserEntity is your user entity class
        mockUser.setId(userId);
        mockUser.setEmail("a.getov@gmail.com");
        MultipartFile mockImage = mock(MultipartFile.class);
        String mockUrl = "http://example.com/profile.jpg";
        AzureBlobService azureBlobService = mock(AzureBlobService.class);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        UpdateUserProfileImageUseCaseImpl updateUserProfileImageUseCase = new UpdateUserProfileImageUseCaseImpl(azureBlobService, userRepository);

        // Assert
        assertThrows(InvalidUserIdException.class, () -> updateUserProfileImageUseCase.updateProfileImage(userId, mockImage));
    }

    /**
     * @verifies throw DataOwnershipViolationException when logged user is not the same
     * @see UpdateUserProfileImageUseCaseImpl#updateProfileImage(Long, org.springframework.web.multipart.MultipartFile)
     */
    @Test
    @WithMockUser(username = "non.existant.user@gmail.com")
    public void updateProfileImage_shouldThrowDataOwnershipViolationExceptionWhenLoggedUserIsNotTheSame() throws Exception {
        // Arrange
        Long userId = 1L;
        UserEntity mockUser = new UserEntity(); // Assuming UserEntity is your user entity class
        mockUser.setId(userId);
        mockUser.setEmail("a.getov@gmail.com");
        MultipartFile mockImage = mock(MultipartFile.class);
        String mockUrl = "http://example.com/profile.jpg";
        AzureBlobService azureBlobService = mock(AzureBlobService.class);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        UpdateUserProfileImageUseCaseImpl updateUserProfileImageUseCase = new UpdateUserProfileImageUseCaseImpl(azureBlobService, userRepository);

        // Assert
        assertThrows(DataOwnershipViolationException.class, () -> updateUserProfileImageUseCase.updateProfileImage(userId, mockImage));
    }
}
