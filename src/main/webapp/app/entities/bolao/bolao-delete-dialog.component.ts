import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bolao } from './bolao.model';
import { BolaoPopupService } from './bolao-popup.service';
import { BolaoService } from './bolao.service';

@Component({
    selector: 'jhi-bolao-delete-dialog',
    templateUrl: './bolao-delete-dialog.component.html'
})
export class BolaoDeleteDialogComponent {

    bolao: Bolao;

    constructor(
        private bolaoService: BolaoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bolaoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bolaoListModification',
                content: 'Deleted an bolao'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bolao-delete-popup',
    template: ''
})
export class BolaoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bolaoPopupService: BolaoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bolaoPopupService
                .open(BolaoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
