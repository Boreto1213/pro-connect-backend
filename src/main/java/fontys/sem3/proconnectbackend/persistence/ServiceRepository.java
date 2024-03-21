package fontys.sem3.proconnectbackend.persistence;

import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    ArrayList<ServiceEntity> findServiceEntitiesByExpert_Id(Long id);
    Page<ServiceEntity> findServiceEntitiesByTitleContainingAndPriceBetween(String titleQuery, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
