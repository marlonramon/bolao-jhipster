import { BaseEntity,  } from './../../shared';
import { Campeonato } from './../campeonato';

export class Bolao implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string,
        public pontosAcertoDoisPlacares?: number,
        public pontosAcertoUmPlacar?: number,
        public pontosAcertoResultado?: number,
        public campeonatoDTO?: Campeonato
    ) {
    }
}
