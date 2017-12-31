import { BaseEntity, User } from './../../shared';

export const enum GENDER {
    'MALE',
    'FEMALE'
}

export class UserData implements BaseEntity {
    constructor(
        public id?: number,
        public birthDate?: any,
        public gender?: GENDER,
        public pesel?: string,
        public phoneNumber?: string,
        public user?: User,
        public principle?: BaseEntity,
        public agent?: BaseEntity,
    ) {
    }
}
