import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Rodada } from './rodada.model';
import { RodadaService } from './rodada.service';

@Injectable()
export class RodadaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rodadaService: RodadaService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rodadaService.find(id)
                    .subscribe((rodadaResponse: HttpResponse<Rodada>) => {
                        const rodada: Rodada = rodadaResponse.body;
                        rodada.inicioRodada = this.datePipe
                            .transform(rodada.inicioRodada, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.rodadaModalRef(component, rodada);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rodadaModalRef(component, new Rodada());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rodadaModalRef(component: Component, rodada: Rodada): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.rodada = rodada;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
