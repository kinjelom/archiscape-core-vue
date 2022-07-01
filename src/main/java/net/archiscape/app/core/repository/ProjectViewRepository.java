package net.archiscape.app.core.repository;

import java.util.List;
import java.util.Optional;
import net.archiscape.app.core.domain.ProjectView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectView entity.
 */
@Repository
public interface ProjectViewRepository extends ProjectViewRepositoryWithBagRelationships, JpaRepository<ProjectView, Long> {
    default Optional<ProjectView> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<ProjectView> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<ProjectView> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
