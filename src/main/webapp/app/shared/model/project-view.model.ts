import { IProject } from '@/shared/model/project.model';
import { IProjectElement } from '@/shared/model/project-element.model';

import { C4ViewLevel } from '@/shared/model/enumerations/c-4-view-level.model';
export interface IProjectView {
  id?: number;
  name?: string | null;
  documentation?: string | null;
  c4level?: C4ViewLevel;
  project?: IProject;
  projectElements?: IProjectElement[];
}

export class ProjectView implements IProjectView {
  constructor(
    public id?: number,
    public name?: string | null,
    public documentation?: string | null,
    public c4level?: C4ViewLevel,
    public project?: IProject,
    public projectElements?: IProjectElement[]
  ) {}
}