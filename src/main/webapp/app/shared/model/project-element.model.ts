import { IProject } from '@/shared/model/project.model';

import { ElementType } from '@/shared/model/enumerations/element-type.model';
export interface IProjectElement {
  id?: number;
  name?: string;
  type?: ElementType;
  documentation?: string | null;
  landscapeElementId?: string | null;
  project?: IProject;
}

export class ProjectElement implements IProjectElement {
  constructor(
    public id?: number,
    public name?: string,
    public type?: ElementType,
    public documentation?: string | null,
    public landscapeElementId?: string | null,
    public project?: IProject
  ) {}
}
