package fontys.sem3.proconnectbackend.business.usecases.user.impl;

import fontys.sem3.proconnectbackend.business.dtos.UpdateUserRequest;
import fontys.sem3.proconnectbackend.business.exeptions.DataOwnershipViolationException;
import fontys.sem3.proconnectbackend.business.usecases.user.UpdateUserUseCase;
import fontys.sem3.proconnectbackend.configuration.AzureBlobService;
import fontys.sem3.proconnectbackend.configuration.security.DataOwnershipValidator;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import fontys.sem3.proconnectbackend.persistence.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AzureBlobService azureBlobService;

    /**
     *
     * @param request object containing all necessary values to update a User
     * @should return Optional<userId> when the userId exists
     * @should return Optional<null> when the userId exists
     * @should throw DataOwnershipViolationException when logged user is not the sameÏ
     */
    @Override
    public Optional<Long> updateUser(@Valid UpdateUserRequest request) throws DataOwnershipViolationException {
        Optional<UserEntity> userEntityOptional = userRepository.findById(request.getId());
        if (userEntityOptional.isEmpty()) {
            return  Optional.empty();
        }

        if (!DataOwnershipValidator.canUserModifyUserResource(userEntityOptional.get())) {
            throw new DataOwnershipViolationException();
        }

        UserEntity user = userEntityOptional.get();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());

        if (user instanceof ExpertEntity expert) {
            expert.setBio(request.getBio());
            expert.setProfession(request.getProfession());
            expert.setYearsOfExperience(request.getYearsOfExperience());
        }

        userRepository.save(user);

        return Optional.of(request.getId());
    }
}
