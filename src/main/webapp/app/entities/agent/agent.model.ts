import { BaseEntity } from './../../shared';

export const enum LEGAL_PERSONALITY {
    'COMPANY',
    'PERSON'
}

export const enum AGENT_STATUS {
    'ACTIVE',
    'INACTIVE',
    'SUSPENDED',
    'DELETED',
    'VACATION'
}

export class Agent implements BaseEntity {
    constructor(
        public id?: number,
        public shortDescription?: string,
        public fullDescription?: string,
        public legalPersonality?: LEGAL_PERSONALITY,
        public agentStatus?: AGENT_STATUS,
        public preferredRate?: number,
        public campaignActivities?: BaseEntity[],
    ) {
    }
}
