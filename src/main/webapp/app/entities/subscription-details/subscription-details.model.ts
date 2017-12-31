import { BaseEntity } from './../../shared';

export class SubscriptionDetails implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public desctiprion?: string,
        public numberOfCampaigns?: number,
        public price?: number,
    ) {
    }
}
