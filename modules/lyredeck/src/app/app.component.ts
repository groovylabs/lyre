import {Component, ViewChild} from '@angular/core';

import {StompService} from 'ng2-stomp-service';
import {Registry} from "../domain/Registry";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {

    @ViewChild('sidenav') sidenav;

    registry: Registry;

    context: String = 'endpoints';

    route(context: String): void {
        this.context = context;
        this.sidenav.close()
    }

    constructor(stomp: StompService) {
        this.registry = new Registry(stomp);
    }
}
