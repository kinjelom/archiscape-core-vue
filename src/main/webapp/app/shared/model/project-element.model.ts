import { IProject } from '@/shared/model/project.model';

import { ElementType } from '@/shared/model/enumerations/element-type.model';
export interface IProjectElement {
  id?: number;
  name?: string | null;
  documentation?: string | null;
  technology?: string | null;
  type?: ElementType;
  landscapeElementId?: string | null;
  extElementId?: string | null;
  extSourceElementId?: string | null;
  extTargetElementId?: string | null;
  project?: IProject;
}

export class ProjectElement implements IProjectElement {
  constructor(
    public id?: number,
    public name?: string | null,
    public documentation?: string | null,
    public technology?: string | null,
    public type?: ElementType,
    public landscapeElementId?: string | null,
    public extElementId?: string | null,
    public extSourceElementId?: string | null,
    public extTargetElementId?: string | null,
    public project?: IProject
  ) {}
}
