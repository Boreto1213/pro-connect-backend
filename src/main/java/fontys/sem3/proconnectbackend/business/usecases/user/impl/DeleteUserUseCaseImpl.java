package fontys.sem3.proconnectbackend.business.usecases.user.impl;

import fontys.sem3.proconnectbackend.business.exeptions.DataOwnershipViolationException;
import fontys.sem3.proconnectbackend.business.usecases.user.DeleteUserUseCase;
import fontys.sem3.proconnectbackend.configuration.security.DataOwnershipValidator;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UserRepository userRepository;

    /**
     *
     * @param id the id of the user to be deleted
     * @should return Optional<userId> when the userId exists
     * @should return Optional<null> when the userId does not exist
     *
     */
    @Override
    @Transactional
    public Optional<Long> deleteUser(@NotNull Long id) throws DataOwnershipViolationException {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (!DataOwnershipValidator.canUserModifyUserResource(user.get())) {
                throw new DataOwnershipViolationException();
            }

            userRepository.delete(user.get());

            return Optional.of(id);
        }

        return Optional.empty();
    }
}
