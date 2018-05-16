import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Aposta } from './aposta.model';
import { ApostaPopupService } from './aposta-popup.service';
import { ApostaService } from './aposta.service';

@Component({
    selector: 'jhi-aposta-delete-dialog',
    templateUrl: './aposta-delete-dialog.component.html'
})
export class ApostaDeleteDialogComponent {

    aposta: Aposta;

    constructor(
        private apostaService: ApostaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.apostaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'apostaListModification',
                content: 'Deleted an aposta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-aposta-delete-popup',
    template: ''
})
export class ApostaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private apostaPopupService: ApostaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.apostaPopupService
                .open(ApostaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
