package fontys.sem3.proconnectbackend.business.usecases.user;

import fontys.sem3.proconnectbackend.domain.User;

import java.util.Optional;

public interface GetUserByIdUseCase {
    Optional<User> getUserById(Long id);

}
