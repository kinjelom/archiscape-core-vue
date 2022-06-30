package net.archiscape.app.core.service;

import java.util.Optional;
import net.archiscape.app.core.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Team}.
 */
public interface TeamService {
    /**
     * Save a team.
     *
     * @param team the entity to save.
     * @return the persisted entity.
     */
    Team save(Team team);

    /**
     * Updates a team.
     *
     * @param team the entity to update.
     * @return the persisted entity.
     */
    Team update(Team team);

    /**
     * Partially updates a team.
     *
     * @param team the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Team> partialUpdate(Team team);

    /**
     * Get all the teams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Team> findAll(Pageable pageable);

    /**
     * Get all the teams with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Team> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" team.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Team> findOne(String id);

    /**
     * Delete the "id" team.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
