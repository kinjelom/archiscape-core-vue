import { ILandscape } from '@/shared/model/landscape.model';

import { ElementType } from '@/shared/model/enumerations/element-type.model';
export interface ILandscapeElement {
  id?: string;
  name?: string;
  documentation?: string | null;
  technology?: string | null;
  type?: ElementType;
  landscape?: ILandscape;
}

export class LandscapeElement implements ILandscapeElement {
  constructor(
    public id?: string,
    public name?: string,
    public documentation?: string | null,
    public technology?: string | null,
    public type?: ElementType,
    public landscape?: ILandscape
  ) {}
}
