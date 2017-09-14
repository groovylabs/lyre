import { Component, ViewChild } from '@angular/core';

import { StompService } from 'ng2-stomp-service';
import { Registry } from "../domain/Registry";
import { DialogServerConnect } from "../views/components/dialog-server-connect/dialog-server-connect";
import { MdDialog } from "@angular/material";
import { LocalStorageService } from "../services/local-storage-service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {

    @ViewChild('sidenav') sidenav;

    registry: Registry;

    context: String = 'endpoints';

    constructor(public dialog: MdDialog, public storageService: LocalStorageService,
        stomp: StompService) {
        this.registry = new Registry(stomp);
        this.connectToServer();
    }

    route(context: String): void {
        this.context = context;
        this.sidenav.close();
    }

    connectToServer(): void{
        console.log("inside on method connectToServer");
        console.log("this.storageService = " + this.storageService.getItem("rememberMe"));

        if(this.storageService.getItem("rememberMe") == 'false') {
            let dialogRef = this.dialog.open(DialogServerConnect, {
              width: '320px',
              height: '320px',
              data: {
                  host: 'http://localhost:8243/lyre',
                  rememberMe: false
              }
            });

            dialogRef.afterClosed().subscribe(result => {
                console.log(result);
                console.log('The dialog was closed and the result was printed above.');

                if (result.rememberMe) {
                    this.storageService.setItem("rememberMe", result.rememberMe);
                }
            });
        }
    }
}
