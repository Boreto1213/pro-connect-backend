package fontys.sem3.proconnectbackend.business.usecases.user.impl;

import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteUserUseCaseImplTest {
    /**
     * @verifies return Optional<userId> when the userId exists
     * @see DeleteUserUseCaseImpl#deleteUser(Long)
     */
    @Test
    @WithMockUser(username = "g.alex@gmail.com")
    public void deleteUser_shouldReturnOptionalUserIdWhenTheUserIdExists() throws Exception {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        Long existingUserId = 1L;

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
        DeleteUserUseCaseImpl updateUserUseCaseImpl = new DeleteUserUseCaseImpl(userRepository);
        Optional<Long> actualResponse = updateUserUseCaseImpl.deleteUser(existingUserId);
        Optional<Long> expectedResponse = Optional.ofNullable(existingUserId);
        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(userRepository).findById(existingUserId);
    }

    /**
     * @verifies return Optional<null> when the userId does not exist
     * @see DeleteUserUseCaseImpl#deleteUser(Long)
     */
    @Test
    public void deleteUser_shouldReturnOptionalnullWhenTheUserIdDoesNotExist() throws Exception {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        Long nonExistingUserId = 1L;



        when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());
        // Act
        DeleteUserUseCaseImpl updateUserUseCaseImpl = new DeleteUserUseCaseImpl(userRepository);
        Optional<Long> actualResponse = updateUserUseCaseImpl.deleteUser(nonExistingUserId);
        Optional<Long> expectedResponse = Optional.empty();
        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(userRepository).findById(nonExistingUserId);
    }
}
