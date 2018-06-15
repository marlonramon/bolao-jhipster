export class Ranking  {
    constructor(
        public idBolao?: number,
        public login?: string,
        public username?: string,
        public pontuacaoAtual?: number,
        public apostaCerteira?: number,
        public apostaResultado?: number,
        public apostaUmPlacar?: number,
    ) {
    }
}
