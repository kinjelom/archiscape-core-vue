package net.archiscape.app.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import net.archiscape.app.core.domain.enumeration.C4ViewLevel;

/**
 * A ProjectView.
 */
@Entity
@Table(name = "project_view")
public class ProjectView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @Size(max = 2048)
    @Column(name = "documentation", length = 2048)
    private String documentation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "c_4_level", nullable = false)
    private C4ViewLevel c4level;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "landscape", "team" }, allowSetters = true)
    private Project project;

    @ManyToMany
    @NotNull
    @JoinTable(
        name = "rel_project_view__project_element",
        joinColumns = @JoinColumn(name = "project_view_id"),
        inverseJoinColumns = @JoinColumn(name = "project_element_id")
    )
    @JsonIgnoreProperties(value = { "project", "projectViews" }, allowSetters = true)
    private Set<ProjectElement> projectElements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProjectView id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ProjectView name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentation() {
        return this.documentation;
    }

    public ProjectView documentation(String documentation) {
        this.setDocumentation(documentation);
        return this;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public C4ViewLevel getc4level() {
        return this.c4level;
    }

    public ProjectView c4level(C4ViewLevel c4level) {
        this.setc4level(c4level);
        return this;
    }

    public void setc4level(C4ViewLevel c4level) {
        this.c4level = c4level;
    }

    public byte[] getImage() {
        return this.image;
    }

    public ProjectView image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public ProjectView imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ProjectView project(Project project) {
        this.setProject(project);
        return this;
    }

    public Set<ProjectElement> getProjectElements() {
        return this.projectElements;
    }

    public void setProjectElements(Set<ProjectElement> projectElements) {
        this.projectElements = projectElements;
    }

    public ProjectView projectElements(Set<ProjectElement> projectElements) {
        this.setProjectElements(projectElements);
        return this;
    }

    public ProjectView addProjectElement(ProjectElement projectElement) {
        this.projectElements.add(projectElement);
        projectElement.getProjectViews().add(this);
        return this;
    }

    public ProjectView removeProjectElement(ProjectElement projectElement) {
        this.projectElements.remove(projectElement);
        projectElement.getProjectViews().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectView)) {
            return false;
        }
        return id != null && id.equals(((ProjectView) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectView{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", documentation='" + getDocumentation() + "'" +
            ", c4level='" + getc4level() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
