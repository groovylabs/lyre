import { Component, Input, EventEmitter, Output } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";

import { Endpoint } from "../../../../domain/Endpoint";
import { StorageService } from "../../../../domain/services/storage.service";

@Component({
    selector: 'endpoint-actions',
    templateUrl: './endpoint-actions.html',
    styleUrls: ['./endpoint-actions.scss']
})

export class EndpointActions {

    private openLog = false;

    @Input() endpoint : Endpoint;

    @Output('buttonLog') showLogEvent = new EventEmitter();

    @Output('endpointResponse') endpointResponseEvent = new EventEmitter();

    constructor(private http: HttpClient, public storageService : StorageService) {
    }

    doRequest() {
        // TODO: Change this to "this.http.doRequest".
        switch(this.endpoint.method) {

            case 'GET':
                this.http.get(this.storageService.getItem("host") + this.endpoint.path,
                        {observe : "response"}).subscribe(resp => {
                    this.endpointResponse(resp);
                });
                break;
            default:
                console.log('the requisition cant be applied.');

        }
    }

    logAction(openLog : boolean) {
        this.openLog = openLog;
        this.showLogEvent.emit(openLog);
    }

    endpointResponse(endpointResponse) {
        this.endpointResponseEvent.emit(endpointResponse);
    }

}
