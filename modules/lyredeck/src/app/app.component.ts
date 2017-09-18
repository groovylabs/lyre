import {Component, ViewChild} from '@angular/core';
import {RegistryService} from "../domain/services/registry.service";
import {DialogServerConnect} from "../views/components/dialog-server-connect/dialog-server-connect";
import {MdDialog, MdDialogConfig} from "@angular/material";
import {StorageService} from "../domain/services/storage.service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {

    @ViewChild('sidenav') sidenav;

    context: String = 'endpoints';

    constructor(public dialog: MdDialog, public registryService: RegistryService, public storageService: StorageService) {
        this.connectToServer();
    }

    route(context: String): void {
        this.context = context;
        this.sidenav.close();
    }

    connectToServer(): void {
        console.log("inside on method connectToServer");
        console.log("this.storageService = " + this.storageService.getItem("rememberMe"));

        if (this.storageService.getItem("rememberMe") == 'false') {

            let dialogConfig = new MdDialogConfig();
            dialogConfig.width = '320px';
            dialogConfig.height = '320px';
            dialogConfig.data = {
                host: 'http://localhost:8234/lyre',
                rememberMe:
                    false
            };

            let dialogRef = this.dialog.open(DialogServerConnect, dialogConfig);

            dialogRef.afterClosed().subscribe(result => {
                if (typeof result === 'undefined') {
                    result = {
                        host: "http://localhost:8234/lyre",
                        rememberMe: false
                    };
                }

                console.log(result);
                console.log('The dialog was closed and the result was printed above.');

                this.storageService.setItem("host", result.host);

                if (result.rememberMe) {
                    this.storageService.setItem("rememberMe", result.rememberMe);
                }
            });
        }
    }
}
