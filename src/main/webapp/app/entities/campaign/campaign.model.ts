import { BaseEntity } from './../../shared';

export const enum CAMPAING_STATUS {
    'ACTIVE',
    'INACTIVE',
    'SUSPENDED',
    'DELETED'
}

export class Campaign implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public startDate?: any,
        public endDate?: any,
        public campaignActivity?: string,
        public listOfPostsForActivity?: number,
        public executionStatus?: number,
        public budget?: number,
        public campaignStatus?: CAMPAING_STATUS,
        public parentCampaign?: number,
        public principleSubscription?: BaseEntity,
    ) {
    }
}
