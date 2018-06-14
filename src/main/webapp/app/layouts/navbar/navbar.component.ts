import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { ProfileService } from '../profiles/profile.service';
import { Principal, LoginModalService, LoginService } from '../../shared';

import { JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs/Subscription';

import { VERSION } from '../../app.constants';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: [
        './navbar.scss'
    ]
})
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    account: Account;
    eventSubscriber: Subscription;

    constructor(
        private loginService: LoginService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router,
        private eventManager: JhiEventManager,
       
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.profileService.getProfileInfo().then((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
        this.refreshAccount();
        this.registerChangeInLogin();

    }

    refreshAccount() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    
    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
        this.account = undefined;
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLogin() {
        this.eventSubscriber = this.eventManager.subscribe('authenticationSuccess', (response) => this.refreshAccount());
    }
}
