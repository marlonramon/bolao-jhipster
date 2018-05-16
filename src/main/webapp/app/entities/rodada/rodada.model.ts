import { BaseEntity } from './../../shared';

export class Rodada implements BaseEntity {
    constructor(
        public id?: number,
        public numero?: number,
        public inicioRodada?: any,
        public campeonatoId?: number,
    ) {
    }
}
