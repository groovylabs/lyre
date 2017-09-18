import { Component, Output, EventEmitter } from '@angular/core';
import { Endpoint } from "../../../domain/Endpoint";

@Component({
    selector: 'endpoints',
    templateUrl: './endpoints.html',
    styleUrls: ['./endpoints.scss']
})

export class Endpoints {

    private endpoint : Endpoint;

    @Output('emitEndpoint') event = new EventEmitter();

    selectedEndpoint(endpoint) {
        this.endpoint = endpoint;
    }

}
