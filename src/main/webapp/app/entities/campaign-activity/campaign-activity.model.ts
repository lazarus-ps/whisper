import { BaseEntity } from './../../shared';

export const enum CAMPAING_ACTIVITY_STATUS {
    'NONE',
    'POSITIVE',
    'NEGATIVE'
}

export const enum ACTIVITY_TYPE {
    'PR',
    'NEUTRAL',
    'COMMENT',
    'ARTICLE'
}

export class CampaignActivity implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public url?: string,
        public creationDate?: any,
        public campaignActivity?: CAMPAING_ACTIVITY_STATUS,
        public text?: string,
        public linkToActivity?: string,
        public nickINActivity?: string,
        public numberOfLinksToCampaignPages?: number,
        public activityType?: ACTIVITY_TYPE,
        public agentRate?: BaseEntity,
        public campaign?: BaseEntity,
        public agents?: BaseEntity[],
    ) {
    }
}
