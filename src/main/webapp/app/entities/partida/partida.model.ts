import { BaseEntity } from './../../shared';

export class Partida implements BaseEntity {
    constructor(
        public id?: number,
        public dataPartida?: any,
        public clubeMandanteId?: number,
        public clubeVisitanteId?: number,
        public rodadaId?: number,
    ) {
    }
}
