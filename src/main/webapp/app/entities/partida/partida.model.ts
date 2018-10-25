import { BaseEntity } from './../../shared';
import { Placar } from './../../shared';
import { Clube } from './../clube';

export class Partida implements BaseEntity {
    constructor(
        public id?: number,
        public dataPartida?: any,
        public clubeMandante?: Clube,
        public clubeVisitante?: Clube,
        public placar: Placar = {
        },
        public rodadaId?: number,
    ) { 
    }
}
