package net.archiscape.app.core.service.impl;

import java.util.Optional;
import net.archiscape.app.core.domain.Team;
import net.archiscape.app.core.repository.TeamRepository;
import net.archiscape.app.core.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Team}.
 */
@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team save(Team team) {
        log.debug("Request to save Team : {}", team);
        return teamRepository.save(team);
    }

    @Override
    public Team update(Team team) {
        log.debug("Request to save Team : {}", team);
        team.setIsPersisted();
        return teamRepository.save(team);
    }

    @Override
    public Optional<Team> partialUpdate(Team team) {
        log.debug("Request to partially update Team : {}", team);

        return teamRepository
            .findById(team.getId())
            .map(existingTeam -> {
                if (team.getDescription() != null) {
                    existingTeam.setDescription(team.getDescription());
                }

                return existingTeam;
            })
            .map(teamRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Team> findAll(Pageable pageable) {
        log.debug("Request to get all Teams");
        return teamRepository.findAll(pageable);
    }

    public Page<Team> findAllWithEagerRelationships(Pageable pageable) {
        return teamRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Team> findOne(String id) {
        log.debug("Request to get Team : {}", id);
        return teamRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Team : {}", id);
        teamRepository.deleteById(id);
    }
}
