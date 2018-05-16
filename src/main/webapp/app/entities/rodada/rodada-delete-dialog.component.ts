import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Rodada } from './rodada.model';
import { RodadaPopupService } from './rodada-popup.service';
import { RodadaService } from './rodada.service';

@Component({
    selector: 'jhi-rodada-delete-dialog',
    templateUrl: './rodada-delete-dialog.component.html'
})
export class RodadaDeleteDialogComponent {

    rodada: Rodada;

    constructor(
        private rodadaService: RodadaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rodadaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'rodadaListModification',
                content: 'Deleted an rodada'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rodada-delete-popup',
    template: ''
})
export class RodadaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rodadaPopupService: RodadaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.rodadaPopupService
                .open(RodadaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
