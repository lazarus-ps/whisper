import { BaseEntity } from './../../shared';

export class AgentRate implements BaseEntity {
    constructor(
        public id?: number,
        public rate?: number,
        public comment?: string,
        public agent?: BaseEntity,
    ) {
    }
}
