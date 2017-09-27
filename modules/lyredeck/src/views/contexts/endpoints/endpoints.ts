import { Component } from '@angular/core';
import { Endpoint } from "../../../domain/Endpoint";

@Component({
    selector: 'endpoints',
    templateUrl: './endpoints.html',
    styleUrls: ['./endpoints.scss']
})

export class Endpoints {

    private endpoint : Endpoint;

    selectedEndpoint(endpoint : Endpoint) {
        this.endpoint = endpoint;
    }

}
