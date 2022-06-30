package net.archiscape.app.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import net.archiscape.app.core.domain.enumeration.ElementType;

/**
 * A ProjectElement.
 */
@Entity
@Table(name = "project_element")
public class ProjectElement implements Serializable {

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

    @Size(max = 50)
    @Column(name = "technology", length = 50)
    private String technology;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ElementType type;

    @Size(max = 30)
    @Column(name = "landscape_element_id", length = 30)
    private String landscapeElementId;

    @Size(max = 100)
    @Column(name = "ext_element_id", length = 100)
    private String extElementId;

    @Size(max = 100)
    @Column(name = "ext_source_element_id", length = 100)
    private String extSourceElementId;

    @Size(max = 100)
    @Column(name = "ext_target_element_id", length = 100)
    private String extTargetElementId;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "landscape", "team" }, allowSetters = true)
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProjectElement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ProjectElement name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentation() {
        return this.documentation;
    }

    public ProjectElement documentation(String documentation) {
        this.setDocumentation(documentation);
        return this;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getTechnology() {
        return this.technology;
    }

    public ProjectElement technology(String technology) {
        this.setTechnology(technology);
        return this;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public ElementType getType() {
        return this.type;
    }

    public ProjectElement type(ElementType type) {
        this.setType(type);
        return this;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public String getLandscapeElementId() {
        return this.landscapeElementId;
    }

    public ProjectElement landscapeElementId(String landscapeElementId) {
        this.setLandscapeElementId(landscapeElementId);
        return this;
    }

    public void setLandscapeElementId(String landscapeElementId) {
        this.landscapeElementId = landscapeElementId;
    }

    public String getExtElementId() {
        return this.extElementId;
    }

    public ProjectElement extElementId(String extElementId) {
        this.setExtElementId(extElementId);
        return this;
    }

    public void setExtElementId(String extElementId) {
        this.extElementId = extElementId;
    }

    public String getExtSourceElementId() {
        return this.extSourceElementId;
    }

    public ProjectElement extSourceElementId(String extSourceElementId) {
        this.setExtSourceElementId(extSourceElementId);
        return this;
    }

    public void setExtSourceElementId(String extSourceElementId) {
        this.extSourceElementId = extSourceElementId;
    }

    public String getExtTargetElementId() {
        return this.extTargetElementId;
    }

    public ProjectElement extTargetElementId(String extTargetElementId) {
        this.setExtTargetElementId(extTargetElementId);
        return this;
    }

    public void setExtTargetElementId(String extTargetElementId) {
        this.extTargetElementId = extTargetElementId;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ProjectElement project(Project project) {
        this.setProject(project);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectElement)) {
            return false;
        }
        return id != null && id.equals(((ProjectElement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectElement{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", documentation='" + getDocumentation() + "'" +
            ", technology='" + getTechnology() + "'" +
            ", type='" + getType() + "'" +
            ", landscapeElementId='" + getLandscapeElementId() + "'" +
            ", extElementId='" + getExtElementId() + "'" +
            ", extSourceElementId='" + getExtSourceElementId() + "'" +
            ", extTargetElementId='" + getExtTargetElementId() + "'" +
            "}";
    }
}