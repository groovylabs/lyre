import { Component, Input, SimpleChanges } from '@angular/core';
import { Endpoint } from "../../../../domain/Endpoint";

@Component({
    selector: 'endpoint-info',
    templateUrl: './endpoint-info.html',
    styleUrls: ['./endpoint-info.scss']
})

export class EndpointInfo {

    private dataResponse : {'status' : null, 'statusText' : null, 'body' : string | null};

    @Input("endpoint") endpoint : Endpoint;

    @Input("endpointResponse") response : any;

    constructor() {
        this.dataResponse = {
            'status' : null,
            'statusText' : null,
            'body' : null
        };
    }

    showBody(): boolean {
        // TODO: Body can be shown when the endpoint has other methods, for example: PUT.
        if(this.endpoint.method === 'POST')
            return false;
        else
            return false;
    }

    ResponseIsNotNull(): boolean {
        if (this.response != null) {
            this.dataResponse.status = this.response.status;
            this.dataResponse.statusText = this.response.statusText;
            this.dataResponse.body = (this.response.hasOwnProperty("body") && this.response.body != null) ? JSON.stringify(this.response.body) : null;

            return true;
        } else
            return false;
    }

    clear() {
        this.response = null;
        this.dataResponse = {
            'status' : null,
            'statusText' : null,
            'body' : null
        };;
    }

    ngOnChanges(changes: SimpleChanges) {
        if(changes['endpoint'] && typeof changes.endpoint.previousValue !== 'undefined' && changes['endpoint'])
            this.clear();
    }

}
