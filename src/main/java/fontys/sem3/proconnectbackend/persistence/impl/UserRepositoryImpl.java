package fontys.sem3.proconnectbackend.persistence.impl;

import fontys.sem3.proconnectbackend.persistence.UserRepositoryEntityManager;
import fontys.sem3.proconnectbackend.persistence.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepositoryEntityManager {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(UserEntity.class, id));
    }

    @Override
    public boolean emailExists(String email) {
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class);
        query.setParameter("email", email);

        try {
            query.getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void save(UserEntity user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(UserEntity user) {
            entityManager.remove(user);
    }

    @Override
    public List<UserEntity> findAll() {
        String jpql = "SELECT e FROM UserEntity e";
        TypedQuery<UserEntity> query = entityManager.createQuery(jpql, UserEntity.class);

        return query.getResultList();
    }

    @Override
    public void update(UserEntity userEntity) {
        UserEntity searchResult = entityManager.find(UserEntity.class, userEntity.getId());

        if (searchResult == null) {
            throw new EntityNotFoundException("Entity with ID " + userEntity.getId() + " not found.");
        }

        entityManager.merge(userEntity);
    }
}
