package fontys.sem3.proconnectbackend.business.usecases.user;

import java.util.Optional;

public interface DeleteUserUseCase {
    Optional<Long> deleteUser(Long id);
}
