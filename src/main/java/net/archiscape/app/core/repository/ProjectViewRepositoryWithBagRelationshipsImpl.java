package net.archiscape.app.core.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.archiscape.app.core.domain.ProjectView;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ProjectViewRepositoryWithBagRelationshipsImpl implements ProjectViewRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ProjectView> fetchBagRelationships(Optional<ProjectView> projectView) {
        return projectView.map(this::fetchProjectElements);
    }

    @Override
    public Page<ProjectView> fetchBagRelationships(Page<ProjectView> projectViews) {
        return new PageImpl<>(
            fetchBagRelationships(projectViews.getContent()),
            projectViews.getPageable(),
            projectViews.getTotalElements()
        );
    }

    @Override
    public List<ProjectView> fetchBagRelationships(List<ProjectView> projectViews) {
        return Optional.of(projectViews).map(this::fetchProjectElements).orElse(Collections.emptyList());
    }

    ProjectView fetchProjectElements(ProjectView result) {
        return entityManager
            .createQuery(
                "select projectView from ProjectView projectView left join fetch projectView.projectElements where projectView is :projectView",
                ProjectView.class
            )
            .setParameter("projectView", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<ProjectView> fetchProjectElements(List<ProjectView> projectViews) {
        return entityManager
            .createQuery(
                "select distinct projectView from ProjectView projectView left join fetch projectView.projectElements where projectView in :projectViews",
                ProjectView.class
            )
            .setParameter("projectViews", projectViews)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
