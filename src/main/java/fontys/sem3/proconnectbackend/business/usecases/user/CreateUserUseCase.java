package fontys.sem3.proconnectbackend.business.usecases.user;

import fontys.sem3.proconnectbackend.business.dtos.CreateUserRequest;

public interface CreateUserUseCase {
    void createUser(CreateUserRequest request);
}
