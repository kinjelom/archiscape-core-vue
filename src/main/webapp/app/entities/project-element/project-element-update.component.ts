import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import ProjectService from '@/entities/project/project.service';
import { IProject } from '@/shared/model/project.model';

import { IProjectElement, ProjectElement } from '@/shared/model/project-element.model';
import ProjectElementService from './project-element.service';
import { ElementType } from '@/shared/model/enumerations/element-type.model';

const validations: any = {
  projectElement: {
    name: {
      required,
    },
    type: {
      required,
    },
    documentation: {},
    landscapeElementId: {
      maxLength: maxLength(30),
    },
    project: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ProjectElementUpdate extends Vue {
  @Inject('projectElementService') private projectElementService: () => ProjectElementService;
  @Inject('alertService') private alertService: () => AlertService;

  public projectElement: IProjectElement = new ProjectElement();

  @Inject('projectService') private projectService: () => ProjectService;

  public projects: IProject[] = [];
  public elementTypeValues: string[] = Object.keys(ElementType);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectElementId) {
        vm.retrieveProjectElement(to.params.projectElementId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.projectElement.id) {
      this.projectElementService()
        .update(this.projectElement)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('archiscapeCoreApp.projectElement.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.projectElementService()
        .create(this.projectElement)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('archiscapeCoreApp.projectElement.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveProjectElement(projectElementId): void {
    this.projectElementService()
      .find(projectElementId)
      .then(res => {
        this.projectElement = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.projectService()
      .retrieve()
      .then(res => {
        this.projects = res.data;
      });
  }
}