import {Component, Inject} from '@angular/core';
import { MdDialogRef, MD_DIALOG_DATA } from "@angular/material";

@Component({
    selector: 'dialog-server-connect',
    templateUrl: './dialog-server-connect.html',
    styleUrls: ['./dialog-server-connect.scss']
})

export class DialogServerConnect {

    constructor(
        public dialogRef: MdDialogRef<DialogServerConnect>,
        @Inject(MD_DIALOG_DATA) public data: any) {}

    closeDialog() {
        this.dialogRef.close(this.data);
    }

}
