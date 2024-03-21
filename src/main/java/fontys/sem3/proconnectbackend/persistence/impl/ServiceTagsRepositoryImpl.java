package fontys.sem3.proconnectbackend.persistence.impl;

import fontys.sem3.proconnectbackend.persistence.ServiceTagsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceTagsRepositoryImpl implements ServiceTagsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addTagToService(Long serviceId, Long tagId) {
        String sql = "INSERT INTO service_tags (service_id, tag_id) VALUES (?, ?)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, serviceId);
        query.setParameter(2, tagId);
        query.executeUpdate();
    }

    @Override
    public void removeTagToService(Long serviceId, Long tagId) {
        String sql = "DELETE FROM service_tags WHERE service_id = ? AND tag_id = ?";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, serviceId);
        query.setParameter(2, tagId);
        query.executeUpdate();
    }

    @Override
    public void getTagsByServiceId(Long serviceId) {
        String sql = "SELECT FROM service_tags WHERE service_id = ?";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, serviceId);
        query.executeUpdate();
    }
}
