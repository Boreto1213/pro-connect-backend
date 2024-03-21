package fontys.sem3.proconnectbackend.business.usecases.user.impl;

import fontys.sem3.proconnectbackend.business.converters.ClientConverter;
import fontys.sem3.proconnectbackend.business.converters.ExpertConverter;
import fontys.sem3.proconnectbackend.business.usecases.user.GetUserByIdUseCase;
import fontys.sem3.proconnectbackend.domain.User;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {
    UserRepository userRepository;

    /**
     *
     * @param id corresponding to one user account
     * @return Optional<User> object
     *
     * @should return an Optional<User> object if such object with corresponding id is present
     * @should return Optional<null> if not such object is present
     */
    @Override
    public Optional<User> getUserById(@NotEmpty Long id) {
        return userRepository.findById(id).map(userEntity -> {
            if (userEntity instanceof ClientEntity clientEntity) {
                return ClientConverter.convert(clientEntity);
            }
            else if (userEntity instanceof ExpertEntity expertEntity) {
                return ExpertConverter.convert(expertEntity);
            }

            return null;
        });
    }
}
