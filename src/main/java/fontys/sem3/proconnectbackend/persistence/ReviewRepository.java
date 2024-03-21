package fontys.sem3.proconnectbackend.persistence;

import fontys.sem3.proconnectbackend.persistence.entity.ReviewEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;


public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    public ArrayList<ReviewEntity> findReviewEntitiesByService(ServiceEntity serviceEntity);
}
