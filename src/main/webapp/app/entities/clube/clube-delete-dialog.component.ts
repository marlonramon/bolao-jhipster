import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Clube } from './clube.model';
import { ClubePopupService } from './clube-popup.service';
import { ClubeService } from './clube.service';

@Component({
    selector: 'jhi-clube-delete-dialog',
    templateUrl: './clube-delete-dialog.component.html'
})
export class ClubeDeleteDialogComponent {

    clube: Clube;

    constructor(
        private clubeService: ClubeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clubeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clubeListModification',
                content: 'Deleted an clube'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-clube-delete-popup',
    template: ''
})
export class ClubeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clubePopupService: ClubePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.clubePopupService
                .open(ClubeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
