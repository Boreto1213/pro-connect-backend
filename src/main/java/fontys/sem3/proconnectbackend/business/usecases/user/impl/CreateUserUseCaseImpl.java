package fontys.sem3.proconnectbackend.business.usecases.user.impl;

import fontys.sem3.proconnectbackend.business.dtos.CreateUserRequest;
import fontys.sem3.proconnectbackend.business.exeptions.EmailAlreadyExistsException;
import fontys.sem3.proconnectbackend.business.usecases.user.CreateUserUseCase;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     *
     * @param request object containing all necessary values to create a User
     * @should throw an exception if the email is already present
     *
     */
    @Override
    public void createUser(@Valid CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // fix later
        if (request.isExpert()) {
            userRepository.save(ExpertEntity.builder()
                    .city(request.getCity())
                    .address(request.getAddress())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(encodedPassword)
                    .phone(request.getPhone())
                    .dislikes(0)
                    .likes(0)
                    .build());
        } else {
            userRepository.save(ClientEntity.builder()
                    .city(request.getCity())
                    .address(request.getAddress())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(encodedPassword)
                    .phone(request.getPhone())
                    .build());
        }
    }
}
