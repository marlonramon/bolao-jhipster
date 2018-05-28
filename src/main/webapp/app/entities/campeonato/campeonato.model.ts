import { BaseEntity } from './../../shared';
import { Rodada } from './../rodada';

export class Campeonato implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string,
        public campeonatoId?: number,
        public rodadas?: Rodada[]
    ) {
    }
}
