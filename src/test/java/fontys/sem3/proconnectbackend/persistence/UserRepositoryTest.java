package fontys.sem3.proconnectbackend.persistence;

import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import fontys.sem3.proconnectbackend.persistence.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace =
        AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        ExpertEntity expert = ExpertEntity.builder()
                .email("g.alex@gmail.com")
                .password("asdASD123@")
                .firstName("Alex")
                .lastName("Gatev")
                .phone("+3161221225")
                .city("Eindhoven")
                .address("Kruisakker 69A")
                .build();
        ClientEntity client = ClientEntity.builder()
                .email("b.simpson@gmail.com")
                .password("asdASD123@")
                .firstName("Bart")
                .lastName("Simpson")
                .phone("+3161221225")
                .city("Eindhoven")
                .address("Kruisakker 69A")
                .build();

        userRepository.save(expert);
        userRepository.save(client);
    }


    @Test
    public void save_shouldSaveUserIfEmailIsUnique() {
        ExpertEntity expert = ExpertEntity.builder()
                .email("b.damianov@gmail.com")
                .password("asdASD123@")
                .firstName("Boris")
                .lastName("Damianov")
                .phone("+3161221225")
                .city("Eindhoven")
                .address("Kruisakker 69A")
                .build();
        userRepository.save(expert);

        String jpql = "SELECT e FROM UserEntity e WHERE e.email = :email";
        TypedQuery<UserEntity> query = entityManager.createQuery(jpql, UserEntity.class);
        query.setParameter("email", "g.alex@gmail.com");
        UserEntity savedUser = query.getSingleResult();
        ExpertEntity expectedUser = ExpertEntity.builder()
                .id(savedUser.getId())
                .email("g.alex@gmail.com")
                .password("asdASD123@")
                .firstName("Alex")
                .lastName("Gatev")
                .phone("+3161221225")
                .city("Eindhoven")
                .address("Kruisakker 69A")
                .build();

        assertEquals(savedUser, expectedUser);
    }

    @Test
    void save_shouldUpdateAlreadyExistingUserEntity() {
        Optional<UserEntity> userEntity = userRepository.findById(1L);

        userEntity.get().setFirstName("Alexander");
        userRepository.save(userEntity.get());

        Optional<UserEntity> updatedUser = userRepository.findById(1L);

        assertEquals(updatedUser.get().getFirstName(), "Alexander");
    }


    @Test
    void findById_shouldReturnTheUserEntityWhenTheIdExists() {
        String jpql = "SELECT e FROM UserEntity e WHERE e.email = :email";
        TypedQuery<UserEntity> query = entityManager.createQuery(jpql, UserEntity.class);
        query.setParameter("email", "g.alex@gmail.com");
        UserEntity user = query.getSingleResult();
        Optional<UserEntity> userEntity = userRepository.findById(user.getId());

        assertNotNull(userEntity.get());
    }

    @Test void findById_shouldReturnEmptyOptionalWhenTheIdDoesNotExist() {
        Optional<UserEntity> userEntity = userRepository.findById(9999L);

        assertEquals(userEntity, Optional.empty());
    }

    @Test void delete_shouldDeleteAlreadyExistingUserEntity() {
        String jpql = "SELECT e FROM UserEntity e WHERE e.email = :email";
        TypedQuery<UserEntity> query = entityManager.createQuery(jpql, UserEntity.class);
        query.setParameter("email", "g.alex@gmail.com");
        UserEntity user = query.getSingleResult();
        Optional<UserEntity> userEntity = userRepository.findById(user.getId());
        userRepository.delete(userEntity.get());

        Optional<UserEntity> deletedUser = userRepository.findById(2L);

        assertEquals(deletedUser, Optional.empty());
    }
}
