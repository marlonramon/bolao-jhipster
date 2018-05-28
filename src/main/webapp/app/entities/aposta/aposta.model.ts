import { BaseEntity } from './../../shared';
import { Partida } from './../partida';
import { Placar } from './../../shared';

export class Aposta implements BaseEntity {
    constructor(
        public id?: number,
        public dataAposta?: any,
        public partida?: Partida,
        public placar?: Placar,
    ) {
    }
}
