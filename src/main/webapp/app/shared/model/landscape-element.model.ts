import { ILandscape } from '@/shared/model/landscape.model';

import { C4ElementType } from '@/shared/model/enumerations/c-4-element-type.model';
export interface ILandscapeElement {
  id?: string;
  name?: string;
  documentation?: string;
  technology?: string | null;
  c4type?: C4ElementType;
  landscape?: ILandscape;
}

export class LandscapeElement implements ILandscapeElement {
  constructor(
    public id?: string,
    public name?: string,
    public documentation?: string,
    public technology?: string | null,
    public c4type?: C4ElementType,
    public landscape?: ILandscape
  ) {}
}
