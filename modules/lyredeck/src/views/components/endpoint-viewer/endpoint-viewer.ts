import { Component, Input } from '@angular/core';
import { Endpoint } from "../../../domain/Endpoint";

@Component({
    selector: 'endpoint-viewer',
    templateUrl: './endpoint-viewer.html',
    styleUrls: ['./endpoint-viewer.scss']
})

export class EndpointViewer {

    @Input() endpoint : Endpoint;

}
