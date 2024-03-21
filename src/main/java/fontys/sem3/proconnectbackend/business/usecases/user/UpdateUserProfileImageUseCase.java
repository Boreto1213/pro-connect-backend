package fontys.sem3.proconnectbackend.business.usecases.user;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UpdateUserProfileImageUseCase {
    void updateProfileImage(Long userId, MultipartFile image) throws IOException;
}
