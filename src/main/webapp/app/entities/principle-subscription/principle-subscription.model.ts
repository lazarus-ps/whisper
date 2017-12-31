import { BaseEntity } from './../../shared';

export const enum PAYMENT_STATUS {
    'DONE',
    'NOTDONE'
}

export class PrincipleSubscription implements BaseEntity {
    constructor(
        public id?: number,
        public paymentToken?: string,
        public startDate?: any,
        public endDate?: any,
        public paymentStatus?: PAYMENT_STATUS,
        public subscriptionDetails?: BaseEntity,
    ) {
    }
}
