import { ILandscape } from '@/shared/model/landscape.model';

import { ElementType } from '@/shared/model/enumerations/element-type.model';
export interface ILandscapeElement {
  id?: string;
  name?: string;
  documentation?: string;
  technology?: string | null;
  type?: ElementType;
  landscape?: ILandscape;
}

export class LandscapeElement implements ILandscapeElement {
  constructor(
    public id?: string,
    public name?: string,
    public documentation?: string,
    public technology?: string | null,
    public type?: ElementType,
    public landscape?: ILandscape
  ) {}
}
