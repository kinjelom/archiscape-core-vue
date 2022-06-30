package net.archiscape.app.core.repository;

import java.util.List;
import java.util.Optional;
import net.archiscape.app.core.domain.ProjectView;
import org.springframework.data.domain.Page;

public interface ProjectViewRepositoryWithBagRelationships {
    Optional<ProjectView> fetchBagRelationships(Optional<ProjectView> projectView);

    List<ProjectView> fetchBagRelationships(List<ProjectView> projectViews);

    Page<ProjectView> fetchBagRelationships(Page<ProjectView> projectViews);
}
