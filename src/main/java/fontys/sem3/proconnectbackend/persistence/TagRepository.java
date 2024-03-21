package fontys.sem3.proconnectbackend.persistence;

import fontys.sem3.proconnectbackend.persistence.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
}
