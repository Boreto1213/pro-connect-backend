package fontys.sem3.proconnectbackend.business.usecases.user.impl;

import fontys.sem3.proconnectbackend.business.dtos.CreateUserRequest;
import fontys.sem3.proconnectbackend.business.exeptions.EmailAlreadyExistsException;
import fontys.sem3.proconnectbackend.business.usecases.user.CreateUserUseCase;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CreateUserUseCaseImplTest {
    /**
     * @verifies return the userId of the newly created User if the email is not present already
     * @see CreateUserUseCaseImpl#createUser(CreateUserRequest)
     */
    @Test
    public void createUser_shouldReturnTheUserIdOfTheNewlyCreatedExpertIfTheEmailIsNotPresentAlready() throws Exception {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        CreateUserRequest requestWithNonExistentEmail = CreateUserRequest.builder()
                .email("b.damianov@gmail.com")
                .password("123ads123")
                .firstName("Boris")
                .lastName("Damianov")
                .phone("+31616022126")
                .city("Eindhoven")
                .address("Kruisakker 60A")
                .isExpert(true)
                .build();
        ExpertEntity expert = ExpertEntity.builder()
                .email(requestWithNonExistentEmail.getEmail())
                .password(passwordEncoder.encode(requestWithNonExistentEmail.getPassword()))
                .firstName(requestWithNonExistentEmail.getFirstName())
                .lastName(requestWithNonExistentEmail.getLastName())
                .phone(requestWithNonExistentEmail.getPhone())
                .city(requestWithNonExistentEmail.getCity())
                .address(requestWithNonExistentEmail.getAddress())
                .build();
        ExpertEntity expertWithId = ExpertEntity.builder()
                .email(requestWithNonExistentEmail.getEmail())
                .password(passwordEncoder.encode(requestWithNonExistentEmail.getPassword()))
                .firstName(requestWithNonExistentEmail.getFirstName())
                .lastName(requestWithNonExistentEmail.getLastName())
                .phone(requestWithNonExistentEmail.getPhone())
                .city(requestWithNonExistentEmail.getCity())
                .address(requestWithNonExistentEmail.getAddress())
                .id(1L)
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(expertWithId));

        // Act
        CreateUserUseCase createUserUseCase = new CreateUserUseCaseImpl(userRepository, passwordEncoder);
        createUserUseCase.createUser(requestWithNonExistentEmail);

        // Assert
        Mockito.verify(userRepository, Mockito.times(1)).save(
                argThat(expertEntity ->
                        expertEntity.getEmail().equals(requestWithNonExistentEmail.getEmail()) &&
                                expertEntity.getFirstName().equals(requestWithNonExistentEmail.getFirstName()) &&
                                expertEntity.getLastName().equals(requestWithNonExistentEmail.getLastName()) &&
                                expertEntity.getPhone().equals(requestWithNonExistentEmail.getPhone()) &&
                                expertEntity.getCity().equals(requestWithNonExistentEmail.getCity()) &&
                                expertEntity.getAddress().equals(requestWithNonExistentEmail.getAddress()) &&
                                // Optionally, check the password is not null or has the expected format
                                expertEntity.getPassword() != null &&
                                expertEntity.getPassword().startsWith("$2a$")
                )
        );
    }

    @Test
    public void createUser_shouldReturnTheUserIdOfTheNewlyCreatedClientIfTheEmailIsNotPresentAlready() throws Exception {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        CreateUserRequest requestWithNonExistentEmail = CreateUserRequest.builder()
                .email("b.damianov@gmail.com")
                .password("123ads123")
                .firstName("Boris")
                .lastName("Damianov")
                .phone("+31616022126")
                .city("Eindhoven")
                .address("Kruisakker 60A")
                .isExpert(false)
                .build();

        ClientEntity clientWithId = ClientEntity.builder()
                .email(requestWithNonExistentEmail.getEmail())
                .password(passwordEncoder.encode(requestWithNonExistentEmail.getPassword()))
                .firstName(requestWithNonExistentEmail.getFirstName())
                .lastName(requestWithNonExistentEmail.getLastName())
                .phone(requestWithNonExistentEmail.getPhone())
                .city(requestWithNonExistentEmail.getCity())
                .address(requestWithNonExistentEmail.getAddress())
                .id(1L)
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(clientWithId));

        // Act
        CreateUserUseCase createUserUseCase = new CreateUserUseCaseImpl(userRepository, passwordEncoder);
        createUserUseCase.createUser(requestWithNonExistentEmail);

        // Assert
        Mockito.verify(userRepository, Mockito.times(1)).save(
                argThat(entity ->
                        entity.getEmail().equals(requestWithNonExistentEmail.getEmail()) &&
                        entity.getFirstName().equals(requestWithNonExistentEmail.getFirstName()) &&
                        entity.getLastName().equals(requestWithNonExistentEmail.getLastName()) &&
                        entity.getPhone().equals(requestWithNonExistentEmail.getPhone()) &&
                        entity.getCity().equals(requestWithNonExistentEmail.getCity()) &&
                        entity.getAddress().equals(requestWithNonExistentEmail.getAddress()) &&
                        // Optionally, check the password is not null or has the expected format
                        entity.getPassword() != null &&
                        entity.getPassword().startsWith("$2a$")
                )
        );
    }

    /**
     * @verifies throw an exception if the email is already present
     * @see CreateUserUseCaseImpl#createUser(CreateUserRequest)
     */
    @Test
    public void createUser_shouldThrowAnExceptionIfTheEmailIsAlreadyPresent() throws Exception {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        CreateUserRequest requestWithAlreadyExistentEmail = CreateUserRequest.builder()
                .email("b.damianov@gmail.com")
                .password("123ads123")
                .firstName("Boris")
                .lastName("Damianov")
                .phone("+31616022126")
                .city("Eindhoven")
                .address("Kruisakker 60A")
                .isExpert(true)
                .build();
        ExpertEntity expert = ExpertEntity.builder()
                .email(requestWithAlreadyExistentEmail.getEmail())
                .password(requestWithAlreadyExistentEmail.getPassword())
                .firstName(requestWithAlreadyExistentEmail.getFirstName())
                .lastName(requestWithAlreadyExistentEmail.getLastName())
                .phone(requestWithAlreadyExistentEmail.getPhone())
                .city(requestWithAlreadyExistentEmail.getCity())
                .address(requestWithAlreadyExistentEmail.getAddress())
                .id(1L)
                .build();
        when(userRepository.existsByEmail(requestWithAlreadyExistentEmail.getEmail())).thenReturn(true);

        // Act
        CreateUserUseCase createUserUseCase = new CreateUserUseCaseImpl(userRepository, passwordEncoder);

        // Assert
        assertThrows(EmailAlreadyExistsException.class, () -> createUserUseCase.createUser(requestWithAlreadyExistentEmail));
        verify(userRepository).existsByEmail(requestWithAlreadyExistentEmail.getEmail());
    }
}
