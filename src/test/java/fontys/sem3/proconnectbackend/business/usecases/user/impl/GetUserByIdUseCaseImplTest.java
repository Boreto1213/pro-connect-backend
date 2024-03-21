package fontys.sem3.proconnectbackend.business.usecases.user.impl;

import fontys.sem3.proconnectbackend.domain.Client;
import fontys.sem3.proconnectbackend.domain.Expert;
import fontys.sem3.proconnectbackend.domain.User;
import fontys.sem3.proconnectbackend.domain.enums.Role;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetUserByIdUseCaseImplTest {
    /**
     * @verifies return an Optional<User> object if such object with corresponding id is present
     * @see GetUserByIdUseCaseImpl#getUserById(Long) (String)
     */
    @Test
    public void getUserById_shouldReturnAnOptionalUserObjectIfSuchObjectWithCorrespondingIdIsPresentWithExpert() throws Exception {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        Long existingId = 1L;
        ExpertEntity user = ExpertEntity.builder()
                .id(existingId)
                .email("b.damianov@gmail.com")
                .phone("+316166022126")
                .city("Eindhoven")
                .firstName("Boris")
                .lastName("Damianov")
                .address("Kruisakker 60A")
                .password("123asd123")
                .build();
        when(userRepository.findById(existingId)).thenReturn(Optional.of(user));

        // Act
        GetUserByIdUseCaseImpl getUserByEmailUseCase = new GetUserByIdUseCaseImpl(userRepository);
        Optional<User> actualResponse = getUserByEmailUseCase.getUserById(existingId);
        Optional<User> expectedResponse = Optional.of(Expert.builder()
                .email(user.getEmail())
                .id(user.getId())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .city(user.getCity())
                .phone(user.getPhone())
                .role(Role.ROLE_Expert)
                .build());

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(userRepository).findById(existingId);
    }

    @Test
    public void getUserById_shouldReturnAnOptionalUserObjectIfSuchObjectWithCorrespondingIdIsPresentWithClient() throws Exception {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        Long existingId = 1L;
        ClientEntity user = ClientEntity.builder()
                .id(existingId)
                .email("b.damianov@gmail.com")
                .phone("+316166022126")
                .city("Eindhoven")
                .firstName("Boris")
                .lastName("Damianov")
                .address("Kruisakker 60A")
                .password("123asd123")
                .build();
        when(userRepository.findById(existingId)).thenReturn(Optional.of(user));

        // Act
        GetUserByIdUseCaseImpl getUserByEmailUseCase = new GetUserByIdUseCaseImpl(userRepository);
        Optional<User> actualResponse = getUserByEmailUseCase.getUserById(existingId);
        Optional<User> expectedResponse = Optional.of(Client.builder()
                .email(user.getEmail())
                .id(user.getId())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .city(user.getCity())
                .phone(user.getPhone())
                .role(Role.ROLE_Client)
                .build());

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(userRepository).findById(existingId);
    }

    /**
     * @verifies return Optional<null> if not such object is present
     * @see GetUserByIdUseCaseImpl#getUserById(Long)
     */
    @Test
    public void getUserByEmail_shouldReturnOptionalNullIfNotSuchObjectIsPresent() throws Exception {
        // Arrange
        UserRepository userRepositoryMock = mock(UserRepository.class);
        Long nonExistentId = 999L;
        when(userRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act
        GetUserByIdUseCaseImpl getUserByIdUseCase = new GetUserByIdUseCaseImpl(userRepositoryMock);
        Optional<User> actualResponse = getUserByIdUseCase.getUserById(nonExistentId);
        Optional<User> expectedResponse = Optional.empty();

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(userRepositoryMock).findById(nonExistentId);
    }
}
