import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import LandscapeService from '@/entities/landscape/landscape.service';
import { ILandscape } from '@/shared/model/landscape.model';

import { ILandscapeElement, LandscapeElement } from '@/shared/model/landscape-element.model';
import LandscapeElementService from './landscape-element.service';
import { ElementType } from '@/shared/model/enumerations/element-type.model';

const validations: any = {
  landscapeElement: {
    type: {
      required,
    },
    documentation: {},
    landscape: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class LandscapeElementUpdate extends Vue {
  @Inject('landscapeElementService') private landscapeElementService: () => LandscapeElementService;
  @Inject('alertService') private alertService: () => AlertService;

  public landscapeElement: ILandscapeElement = new LandscapeElement();

  @Inject('landscapeService') private landscapeService: () => LandscapeService;

  public landscapes: ILandscape[] = [];
  public elementTypeValues: string[] = Object.keys(ElementType);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.landscapeElementId) {
        vm.retrieveLandscapeElement(to.params.landscapeElementId);
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
    if (this.landscapeElement.id) {
      this.landscapeElementService()
        .update(this.landscapeElement)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('archiscapeCoreApp.landscapeElement.updated', { param: param.id });
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
      this.landscapeElementService()
        .create(this.landscapeElement)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('archiscapeCoreApp.landscapeElement.created', { param: param.id });
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

  public retrieveLandscapeElement(landscapeElementId): void {
    this.landscapeElementService()
      .find(landscapeElementId)
      .then(res => {
        this.landscapeElement = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.landscapeService()
      .retrieve()
      .then(res => {
        this.landscapes = res.data;
      });
  }
}