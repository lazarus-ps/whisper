import { BaseEntity } from './../../shared';

export class Address implements BaseEntity {
    constructor(
        public id?: number,
        public placeOrCity?: string,
        public street?: string,
        public province?: string,
        public postalCode?: string,
        public userData?: BaseEntity,
    ) {
    }
}
