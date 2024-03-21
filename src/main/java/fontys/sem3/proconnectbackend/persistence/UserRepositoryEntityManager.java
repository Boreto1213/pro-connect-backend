package fontys.sem3.proconnectbackend.persistence;

import fontys.sem3.proconnectbackend.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryEntityManager {

    Optional<UserEntity> findById(Long id);

    boolean emailExists(String email);

    void save(UserEntity user);

    void delete(UserEntity user);

    List<UserEntity> findAll();

    void update(UserEntity userEntity);
}
