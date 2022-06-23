import { ILandscape } from '@/shared/model/landscape.model';

import { ElementType } from '@/shared/model/enumerations/element-type.model';
export interface ILandscapeElement {
  id?: string;
  type?: ElementType;
  documentation?: string | null;
  landscape?: ILandscape;
}

export class LandscapeElement implements ILandscapeElement {
  constructor(public id?: string, public type?: ElementType, public documentation?: string | null, public landscape?: ILandscape) {}
}
