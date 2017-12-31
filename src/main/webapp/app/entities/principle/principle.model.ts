import { BaseEntity } from './../../shared';

export const enum PRINCIPLE_STATUS {
    'ACTIVE',
    'INACTIVE',
    'SUSPENDED',
    'DELETED'
}

export class Principle implements BaseEntity {
    constructor(
        public id?: number,
        public nip?: string,
        public companyName?: string,
        public principleStatus?: PRINCIPLE_STATUS,
        public principleSubscription?: BaseEntity,
    ) {
    }
}
