package fontys.sem3.proconnectbackend.business.usecases.user.impl;

import fontys.sem3.proconnectbackend.business.exeptions.DataOwnershipViolationException;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidUserIdException;
import fontys.sem3.proconnectbackend.business.usecases.user.UpdateUserProfileImageUseCase;
import fontys.sem3.proconnectbackend.configuration.AzureBlobService;
import fontys.sem3.proconnectbackend.configuration.security.DataOwnershipValidator;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateUserProfileImageUseCaseImpl implements UpdateUserProfileImageUseCase {
    private final AzureBlobService azureBlobService;
    private final UserRepository userRepository;


    /**
     * @should execute when invoked with correct data
     * @should throw InvalidUserIdException when the userId does not exist
     * @should throw DataOwnershipViolationException when logged user is not the same
     */
    @Override
    @Transactional
    public void updateProfileImage(Long userId ,MultipartFile image) throws InvalidUserIdException, IOException, DataOwnershipViolationException {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (userEntityOptional.isEmpty()) {
            throw new InvalidUserIdException();
        }

        if (!DataOwnershipValidator.canUserModifyUserResource(userEntityOptional.get())) {
            throw new DataOwnershipViolationException();
        }

        String profileImageUrl = azureBlobService.upload(image);
        userEntityOptional.get().setProfileImageUrl(profileImageUrl);
        userRepository.save(userEntityOptional.get());
    }
}
