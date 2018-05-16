import { BaseEntity } from './../../shared';

export class Aposta implements BaseEntity {
    constructor(
        public id?: number,
        public dataAposta?: any,
        public partidaId?: number,
    ) {
    }
}
