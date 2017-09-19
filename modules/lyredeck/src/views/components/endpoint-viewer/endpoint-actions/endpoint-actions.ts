import { Component, Input } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";

import { Endpoint } from "../../../../domain/Endpoint";
import { StorageService } from "../../../../domain/services/storage.service";

@Component({
    selector: 'endpoint-actions',
    templateUrl: './endpoint-actions.html',
    styleUrls: ['./endpoint-actions.scss']
})

export class EndpointActions {

    @Input() endpoint : Endpoint;

    constructor(private http: HttpClient, public storageService : StorageService) {
    }

    doRequest() {
        // TODO: Change this to "this.http.doRequest".
        switch(this.endpoint.method) {

            case 'GET':
                this.http.get(this.storageService.getItem("host") + this.endpoint.path).subscribe(data => {
                    //TODO: Set data to de endpoint-action view.
                    console.log(data);
                    console.log('data retrieved by the host = ' + this.storageService.getItem("host") + this.endpoint.path);
                });
                break;
            default:
                console.log('the requisition cant be applied.');

        }
    }

}
