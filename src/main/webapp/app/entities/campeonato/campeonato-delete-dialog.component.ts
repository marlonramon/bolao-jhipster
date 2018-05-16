import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Campeonato } from './campeonato.model';
import { CampeonatoPopupService } from './campeonato-popup.service';
import { CampeonatoService } from './campeonato.service';

@Component({
    selector: 'jhi-campeonato-delete-dialog',
    templateUrl: './campeonato-delete-dialog.component.html'
})
export class CampeonatoDeleteDialogComponent {

    campeonato: Campeonato;

    constructor(
        private campeonatoService: CampeonatoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.campeonatoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'campeonatoListModification',
                content: 'Deleted an campeonato'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-campeonato-delete-popup',
    template: ''
})
export class CampeonatoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private campeonatoPopupService: CampeonatoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.campeonatoPopupService
                .open(CampeonatoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
