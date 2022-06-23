<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="archiscapeCoreApp.projectElement.home.createOrEditLabel"
          data-cy="ProjectElementCreateUpdateHeading"
          v-text="$t('archiscapeCoreApp.projectElement.home.createOrEditLabel')"
        >
          Create or edit a ProjectElement
        </h2>
        <div>
          <div class="form-group" v-if="projectElement.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="projectElement.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('archiscapeCoreApp.projectElement.name')" for="project-element-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="project-element-name"
              data-cy="name"
              :class="{ valid: !$v.projectElement.name.$invalid, invalid: $v.projectElement.name.$invalid }"
              v-model="$v.projectElement.name.$model"
              required
            />
            <div v-if="$v.projectElement.name.$anyDirty && $v.projectElement.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.projectElement.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('archiscapeCoreApp.projectElement.type')" for="project-element-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.projectElement.type.$invalid, invalid: $v.projectElement.type.$invalid }"
              v-model="$v.projectElement.type.$model"
              id="project-element-type"
              data-cy="type"
              required
            >
              <option
                v-for="elementType in elementTypeValues"
                :key="elementType"
                v-bind:value="elementType"
                v-bind:label="$t('archiscapeCoreApp.ElementType.' + elementType)"
              >
                {{ elementType }}
              </option>
            </select>
            <div v-if="$v.projectElement.type.$anyDirty && $v.projectElement.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.projectElement.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('archiscapeCoreApp.projectElement.documentation')"
              for="project-element-documentation"
              >Documentation</label
            >
            <input
              type="text"
              class="form-control"
              name="documentation"
              id="project-element-documentation"
              data-cy="documentation"
              :class="{ valid: !$v.projectElement.documentation.$invalid, invalid: $v.projectElement.documentation.$invalid }"
              v-model="$v.projectElement.documentation.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('archiscapeCoreApp.projectElement.landscapeElementId')"
              for="project-element-landscapeElementId"
              >Landscape Element Id</label
            >
            <input
              type="text"
              class="form-control"
              name="landscapeElementId"
              id="project-element-landscapeElementId"
              data-cy="landscapeElementId"
              :class="{ valid: !$v.projectElement.landscapeElementId.$invalid, invalid: $v.projectElement.landscapeElementId.$invalid }"
              v-model="$v.projectElement.landscapeElementId.$model"
            />
            <div v-if="$v.projectElement.landscapeElementId.$anyDirty && $v.projectElement.landscapeElementId.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.projectElement.landscapeElementId.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 30 })"
              >
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('archiscapeCoreApp.projectElement.project')" for="project-element-project"
              >Project</label
            >
            <select
              class="form-control"
              id="project-element-project"
              data-cy="project"
              name="project"
              v-model="projectElement.project"
              required
            >
              <option v-if="!projectElement.project" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  projectElement.project && projectOption.id === projectElement.project.id ? projectElement.project : projectOption
                "
                v-for="projectOption in projects"
                :key="projectOption.id"
              >
                {{ projectOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.projectElement.project.$anyDirty && $v.projectElement.project.$invalid">
            <small class="form-text text-danger" v-if="!$v.projectElement.project.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.projectElement.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./project-element-update.component.ts"></script>
