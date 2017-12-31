import { BaseEntity } from './../../shared';

export class SampleOfAgentActivity implements BaseEntity {
    constructor(
        public id?: number,
        public activityName?: string,
    ) {
    }
}
