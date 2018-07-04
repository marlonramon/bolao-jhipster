import { BaseEntity } from './../../shared';
import { Campeonato } from '../campeonato/campeonato.model';

export class Rodada implements BaseEntity {
    constructor(
        
        public id?: number,
        public numero?: number,
        public inicioRodada?: any,
        public fimRodada?: any,      
        public campeonatoId?: number,  
        public campeonato?: Campeonato 
        
    ) {
    }
}
