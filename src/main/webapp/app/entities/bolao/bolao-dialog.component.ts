import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Bolao } from './bolao.model';
import { BolaoPopupService } from './bolao-popup.service';
import { BolaoService } from './bolao.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-bolao-dialog',
    templateUrl: './bolao-dialog.component.html'
})
export class BolaoDialogComponent implements OnInit {

    bolao: Bolao;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private bolaoService: BolaoService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bolao.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bolaoService.update(this.bolao));
        } else {
            this.subscribeToSaveResponse(
                this.bolaoService.create(this.bolao));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Bolao>>) {
        result.subscribe((res: HttpResponse<Bolao>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Bolao) {
        this.eventManager.broadcast({ name: 'bolaoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-bolao-popup',
    template: ''
})
export class BolaoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bolaoPopupService: BolaoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bolaoPopupService
                    .open(BolaoDialogComponent as Component, params['id']);
            } else {
                this.bolaoPopupService
                    .open(BolaoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
