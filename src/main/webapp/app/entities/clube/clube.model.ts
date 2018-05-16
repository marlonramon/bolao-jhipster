import { BaseEntity } from './../../shared';

export class Clube implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public bandeiraContentType?: string,
        public bandeira?: any,
    ) {
    }
}
