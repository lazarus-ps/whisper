import { BaseEntity } from './../../shared';

export class SampleOfCampaignActivity implements BaseEntity {
    constructor(
        public id?: number,
        public activityName?: string,
        public listOfPosts?: number,
    ) {
    }
}
