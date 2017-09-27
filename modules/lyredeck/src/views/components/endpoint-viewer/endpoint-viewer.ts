import { Component, Input } from '@angular/core';
import { Endpoint } from "../../../domain/Endpoint";

@Component({
    selector: 'endpoint-viewer',
    templateUrl: './endpoint-viewer.html',
    styleUrls: ['./endpoint-viewer.scss']
})

export class EndpointViewer {

    private openLog = false;

    private response : any;

    @Input() endpoint : Endpoint;

    logAction(openLog : boolean) {
        console.log('inside of endpoint-viewer, the value of openLog is : ' + openLog);
        this.openLog = openLog;
    }

    endpointResponse(endpointResponse) {
        this.response = endpointResponse;
    }

}
