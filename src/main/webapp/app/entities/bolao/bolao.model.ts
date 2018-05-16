import { BaseEntity, User } from './../../shared';

export class Bolao implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string,
        public pontosAcertoDoisPlacares?: number,
        public pontosAcertoUmPlacar?: number,
        public pontosAcertoResultado?: number,
        public usersBolaos?: User[],
    ) {
    }
}
