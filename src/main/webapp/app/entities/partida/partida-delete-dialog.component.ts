import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Partida } from './partida.model';
import { PartidaPopupService } from './partida-popup.service';
import { PartidaService } from './partida.service';

@Component({
    selector: 'jhi-partida-delete-dialog',
    templateUrl: './partida-delete-dialog.component.html'
})
export class PartidaDeleteDialogComponent {

    partida: Partida;

    constructor(
        private partidaService: PartidaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.partidaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'partidaListModification',
                content: 'Deleted an partida'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-partida-delete-popup',
    template: ''
})
export class PartidaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private partidaPopupService: PartidaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.partidaPopupService
                .open(PartidaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
