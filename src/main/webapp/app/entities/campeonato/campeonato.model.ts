import { BaseEntity } from './../../shared';

export class Campeonato implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string,
        public campeonatoId?: number,
    ) {
    }
}
