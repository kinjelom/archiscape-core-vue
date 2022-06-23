<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="archiscapeCoreApp.landscapeElement.home.createOrEditLabel"
          data-cy="LandscapeElementCreateUpdateHeading"
          v-text="$t('archiscapeCoreApp.landscapeElement.home.createOrEditLabel')"
        >
          Create or edit a LandscapeElement
        </h2>
        <div>
          <div class="form-group" v-if="landscapeElement.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="landscapeElement.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('archiscapeCoreApp.landscapeElement.type')" for="landscape-element-type"
              >Type</label
            >
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.landscapeElement.type.$invalid, invalid: $v.landscapeElement.type.$invalid }"
              v-model="$v.landscapeElement.type.$model"
              id="landscape-element-type"
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
            <div v-if="$v.landscapeElement.type.$anyDirty && $v.landscapeElement.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.landscapeElement.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('archiscapeCoreApp.landscapeElement.documentation')"
              for="landscape-element-documentation"
              >Documentation</label
            >
            <input
              type="text"
              class="form-control"
              name="documentation"
              id="landscape-element-documentation"
              data-cy="documentation"
              :class="{ valid: !$v.landscapeElement.documentation.$invalid, invalid: $v.landscapeElement.documentation.$invalid }"
              v-model="$v.landscapeElement.documentation.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('archiscapeCoreApp.landscapeElement.landscape')" for="landscape-element-landscape"
              >Landscape</label
            >
            <select
              class="form-control"
              id="landscape-element-landscape"
              data-cy="landscape"
              name="landscape"
              v-model="landscapeElement.landscape"
              required
            >
              <option v-if="!landscapeElement.landscape" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  landscapeElement.landscape && landscapeOption.id === landscapeElement.landscape.id
                    ? landscapeElement.landscape
                    : landscapeOption
                "
                v-for="landscapeOption in landscapes"
                :key="landscapeOption.id"
              >
                {{ landscapeOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.landscapeElement.landscape.$anyDirty && $v.landscapeElement.landscape.$invalid">
            <small class="form-text text-danger" v-if="!$v.landscapeElement.landscape.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.landscapeElement.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./landscape-element-update.component.ts"></script>
