package fontys.sem3.proconnectbackend.business.usecases.user;

import fontys.sem3.proconnectbackend.business.dtos.UpdateUserRequest;

import java.util.Optional;

public interface UpdateUserUseCase {
    Optional<Long> updateUser(UpdateUserRequest request);
}
