import {Component, OnInit, AfterViewChecked} from '@angular/core';
import {StorageService} from "../../../domain/services/storage.service";

@Component({
    selector: 'settings',
    templateUrl: './settings.html',
    styleUrls: ['./settings.scss']
})

export class Settings implements OnInit, AfterViewChecked {

    private rememberMe: any;

    constructor(public storageService: StorageService) {
    }

    ngOnInit(): void {
        this.rememberMe = (this.storageService.getItem("rememberMe") == 'true');
    }

    // TODO: Checks if we want to check all page because one check-button
    ngAfterViewChecked(): void {
        this.storageService.setItem("rememberMe", this.rememberMe);
    }

}
