package net.archiscape.app.core.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import net.archiscape.app.core.IntegrationTest;
import net.archiscape.app.core.domain.Project;
import net.archiscape.app.core.domain.ProjectElement;
import net.archiscape.app.core.domain.ProjectView;
import net.archiscape.app.core.domain.enumeration.C4ViewLevel;
import net.archiscape.app.core.repository.ProjectViewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ProjectViewResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProjectViewResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTATION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTATION = "BBBBBBBBBB";

    private static final C4ViewLevel DEFAULT_C_4_LEVEL = C4ViewLevel.CONTEXT;
    private static final C4ViewLevel UPDATED_C_4_LEVEL = C4ViewLevel.CONTAINERS;

    private static final String DEFAULT_IMAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_DATA_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/project-views";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectViewRepository projectViewRepository;

    @Mock
    private ProjectViewRepository projectViewRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectViewMockMvc;

    private ProjectView projectView;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectView createEntity(EntityManager em) {
        ProjectView projectView = new ProjectView()
            .name(DEFAULT_NAME)
            .documentation(DEFAULT_DOCUMENTATION)
            .c4level(DEFAULT_C_4_LEVEL)
            .imageName(DEFAULT_IMAGE_NAME)
            .imageData(DEFAULT_IMAGE_DATA)
            .imageDataContentType(DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        projectView.setProject(project);
        // Add required entity
        ProjectElement projectElement;
        if (TestUtil.findAll(em, ProjectElement.class).isEmpty()) {
            projectElement = ProjectElementResourceIT.createEntity(em);
            em.persist(projectElement);
            em.flush();
        } else {
            projectElement = TestUtil.findAll(em, ProjectElement.class).get(0);
        }
        projectView.getProjectElements().add(projectElement);
        return projectView;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectView createUpdatedEntity(EntityManager em) {
        ProjectView projectView = new ProjectView()
            .name(UPDATED_NAME)
            .documentation(UPDATED_DOCUMENTATION)
            .c4level(UPDATED_C_4_LEVEL)
            .imageName(UPDATED_IMAGE_NAME)
            .imageData(UPDATED_IMAGE_DATA)
            .imageDataContentType(UPDATED_IMAGE_DATA_CONTENT_TYPE);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createUpdatedEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        projectView.setProject(project);
        // Add required entity
        ProjectElement projectElement;
        if (TestUtil.findAll(em, ProjectElement.class).isEmpty()) {
            projectElement = ProjectElementResourceIT.createUpdatedEntity(em);
            em.persist(projectElement);
            em.flush();
        } else {
            projectElement = TestUtil.findAll(em, ProjectElement.class).get(0);
        }
        projectView.getProjectElements().add(projectElement);
        return projectView;
    }

    @BeforeEach
    public void initTest() {
        projectView = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectView() throws Exception {
        int databaseSizeBeforeCreate = projectViewRepository.findAll().size();
        // Create the ProjectView
        restProjectViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectView))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectView in the database
        List<ProjectView> projectViewList = projectViewRepository.findAll();
        assertThat(projectViewList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectView testProjectView = projectViewList.get(projectViewList.size() - 1);
        assertThat(testProjectView.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectView.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
        assertThat(testProjectView.getc4level()).isEqualTo(DEFAULT_C_4_LEVEL);
        assertThat(testProjectView.getImageName()).isEqualTo(DEFAULT_IMAGE_NAME);
        assertThat(testProjectView.getImageData()).isEqualTo(DEFAULT_IMAGE_DATA);
        assertThat(testProjectView.getImageDataContentType()).isEqualTo(DEFAULT_IMAGE_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createProjectViewWithExistingId() throws Exception {
        // Create the ProjectView with an existing ID
        projectView.setId(1L);

        int databaseSizeBeforeCreate = projectViewRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectView in the database
        List<ProjectView> projectViewList = projectViewRepository.findAll();
        assertThat(projectViewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkc4levelIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectViewRepository.findAll().size();
        // set the field null
        projectView.setc4level(null);

        // Create the ProjectView, which fails.

        restProjectViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectView))
            )
            .andExpect(status().isBadRequest());

        List<ProjectView> projectViewList = projectViewRepository.findAll();
        assertThat(projectViewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjectViews() throws Exception {
        // Initialize the database
        projectViewRepository.saveAndFlush(projectView);

        // Get all the projectViewList
        restProjectViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectView.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].documentation").value(hasItem(DEFAULT_DOCUMENTATION)))
            .andExpect(jsonPath("$.[*].c4level").value(hasItem(DEFAULT_C_4_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].imageName").value(hasItem(DEFAULT_IMAGE_NAME)))
            .andExpect(jsonPath("$.[*].imageDataContentType").value(hasItem(DEFAULT_IMAGE_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageData").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_DATA))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProjectViewsWithEagerRelationshipsIsEnabled() throws Exception {
        when(projectViewRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProjectViewMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(projectViewRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProjectViewsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(projectViewRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProjectViewMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(projectViewRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getProjectView() throws Exception {
        // Initialize the database
        projectViewRepository.saveAndFlush(projectView);

        // Get the projectView
        restProjectViewMockMvc
            .perform(get(ENTITY_API_URL_ID, projectView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectView.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.documentation").value(DEFAULT_DOCUMENTATION))
            .andExpect(jsonPath("$.c4level").value(DEFAULT_C_4_LEVEL.toString()))
            .andExpect(jsonPath("$.imageName").value(DEFAULT_IMAGE_NAME))
            .andExpect(jsonPath("$.imageDataContentType").value(DEFAULT_IMAGE_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageData").value(Base64Utils.encodeToString(DEFAULT_IMAGE_DATA)));
    }

    @Test
    @Transactional
    void getNonExistingProjectView() throws Exception {
        // Get the projectView
        restProjectViewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjectView() throws Exception {
        // Initialize the database
        projectViewRepository.saveAndFlush(projectView);

        int databaseSizeBeforeUpdate = projectViewRepository.findAll().size();

        // Update the projectView
        ProjectView updatedProjectView = projectViewRepository.findById(projectView.getId()).get();
        // Disconnect from session so that the updates on updatedProjectView are not directly saved in db
        em.detach(updatedProjectView);
        updatedProjectView
            .name(UPDATED_NAME)
            .documentation(UPDATED_DOCUMENTATION)
            .c4level(UPDATED_C_4_LEVEL)
            .imageName(UPDATED_IMAGE_NAME)
            .imageData(UPDATED_IMAGE_DATA)
            .imageDataContentType(UPDATED_IMAGE_DATA_CONTENT_TYPE);

        restProjectViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjectView.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProjectView))
            )
            .andExpect(status().isOk());

        // Validate the ProjectView in the database
        List<ProjectView> projectViewList = projectViewRepository.findAll();
        assertThat(projectViewList).hasSize(databaseSizeBeforeUpdate);
        ProjectView testProjectView = projectViewList.get(projectViewList.size() - 1);
        assertThat(testProjectView.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectView.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
        assertThat(testProjectView.getc4level()).isEqualTo(UPDATED_C_4_LEVEL);
        assertThat(testProjectView.getImageName()).isEqualTo(UPDATED_IMAGE_NAME);
        assertThat(testProjectView.getImageData()).isEqualTo(UPDATED_IMAGE_DATA);
        assertThat(testProjectView.getImageDataContentType()).isEqualTo(UPDATED_IMAGE_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingProjectView() throws Exception {
        int databaseSizeBeforeUpdate = projectViewRepository.findAll().size();
        projectView.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectView.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectView in the database
        List<ProjectView> projectViewList = projectViewRepository.findAll();
        assertThat(projectViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectView() throws Exception {
        int databaseSizeBeforeUpdate = projectViewRepository.findAll().size();
        projectView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectView in the database
        List<ProjectView> projectViewList = projectViewRepository.findAll();
        assertThat(projectViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectView() throws Exception {
        int databaseSizeBeforeUpdate = projectViewRepository.findAll().size();
        projectView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectViewMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectView))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectView in the database
        List<ProjectView> projectViewList = projectViewRepository.findAll();
        assertThat(projectViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectViewWithPatch() throws Exception {
        // Initialize the database
        projectViewRepository.saveAndFlush(projectView);

        int databaseSizeBeforeUpdate = projectViewRepository.findAll().size();

        // Update the projectView using partial update
        ProjectView partialUpdatedProjectView = new ProjectView();
        partialUpdatedProjectView.setId(projectView.getId());

        partialUpdatedProjectView
            .name(UPDATED_NAME)
            .documentation(UPDATED_DOCUMENTATION)
            .imageName(UPDATED_IMAGE_NAME)
            .imageData(UPDATED_IMAGE_DATA)
            .imageDataContentType(UPDATED_IMAGE_DATA_CONTENT_TYPE);

        restProjectViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectView.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectView))
            )
            .andExpect(status().isOk());

        // Validate the ProjectView in the database
        List<ProjectView> projectViewList = projectViewRepository.findAll();
        assertThat(projectViewList).hasSize(databaseSizeBeforeUpdate);
        ProjectView testProjectView = projectViewList.get(projectViewList.size() - 1);
        assertThat(testProjectView.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectView.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
        assertThat(testProjectView.getc4level()).isEqualTo(DEFAULT_C_4_LEVEL);
        assertThat(testProjectView.getImageName()).isEqualTo(UPDATED_IMAGE_NAME);
        assertThat(testProjectView.getImageData()).isEqualTo(UPDATED_IMAGE_DATA);
        assertThat(testProjectView.getImageDataContentType()).isEqualTo(UPDATED_IMAGE_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateProjectViewWithPatch() throws Exception {
        // Initialize the database
        projectViewRepository.saveAndFlush(projectView);

        int databaseSizeBeforeUpdate = projectViewRepository.findAll().size();

        // Update the projectView using partial update
        ProjectView partialUpdatedProjectView = new ProjectView();
        partialUpdatedProjectView.setId(projectView.getId());

        partialUpdatedProjectView
            .name(UPDATED_NAME)
            .documentation(UPDATED_DOCUMENTATION)
            .c4level(UPDATED_C_4_LEVEL)
            .imageName(UPDATED_IMAGE_NAME)
            .imageData(UPDATED_IMAGE_DATA)
            .imageDataContentType(UPDATED_IMAGE_DATA_CONTENT_TYPE);

        restProjectViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectView.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectView))
            )
            .andExpect(status().isOk());

        // Validate the ProjectView in the database
        List<ProjectView> projectViewList = projectViewRepository.findAll();
        assertThat(projectViewList).hasSize(databaseSizeBeforeUpdate);
        ProjectView testProjectView = projectViewList.get(projectViewList.size() - 1);
        assertThat(testProjectView.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectView.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
        assertThat(testProjectView.getc4level()).isEqualTo(UPDATED_C_4_LEVEL);
        assertThat(testProjectView.getImageName()).isEqualTo(UPDATED_IMAGE_NAME);
        assertThat(testProjectView.getImageData()).isEqualTo(UPDATED_IMAGE_DATA);
        assertThat(testProjectView.getImageDataContentType()).isEqualTo(UPDATED_IMAGE_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingProjectView() throws Exception {
        int databaseSizeBeforeUpdate = projectViewRepository.findAll().size();
        projectView.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectView.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectView in the database
        List<ProjectView> projectViewList = projectViewRepository.findAll();
        assertThat(projectViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectView() throws Exception {
        int databaseSizeBeforeUpdate = projectViewRepository.findAll().size();
        projectView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectView in the database
        List<ProjectView> projectViewList = projectViewRepository.findAll();
        assertThat(projectViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectView() throws Exception {
        int databaseSizeBeforeUpdate = projectViewRepository.findAll().size();
        projectView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectViewMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectView))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectView in the database
        List<ProjectView> projectViewList = projectViewRepository.findAll();
        assertThat(projectViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectView() throws Exception {
        // Initialize the database
        projectViewRepository.saveAndFlush(projectView);

        int databaseSizeBeforeDelete = projectViewRepository.findAll().size();

        // Delete the projectView
        restProjectViewMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectView.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectView> projectViewList = projectViewRepository.findAll();
        assertThat(projectViewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
