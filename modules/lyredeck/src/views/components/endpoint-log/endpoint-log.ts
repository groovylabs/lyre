import {Component, AfterViewChecked, OnInit, ElementRef, ViewChild} from '@angular/core';

import {Endpoint} from "../../../domain/Endpoint";
import {LogMessages} from "../../../domain/LogMessages";

@Component({
    selector: 'endpoint-log',
    templateUrl: './endpoint-log.html',
    styleUrls: ['./endpoint-log.scss']
})

export class EndpointLog implements OnInit, AfterViewChecked {

    @ViewChild('scrollMe') public myScrollContainer: ElementRef;

    endpoint: Endpoint;

    constructor() {
        this.endpoint = {
            method: "GET",
            path: "/api/ping",
            logs: [
                {message: "2017-08-21 14:42:12.027  INFO 3429 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 9000 (http)"},
                {message: "2017-08-21 14:42:12.027  INFO 3429 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 9000 (http)"},
                {message: "2017-08-21 14:42:12.027  INFO 3429 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 9000 (http)"},
                {message: "2017-08-21 14:42:12.027  INFO 3429 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 9000 (http)"},
                {message: "2017-08-21 14:42:12.027  INFO 3429 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 9000 (http)"},
                {message: "2017-08-21 14:42:12.027  INFO 3429 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 9000 (http)"},
                {message: "2017-08-21 14:42:12.027  INFO 3429 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 9000 (http)"},
                {message: "2017-08-21 14:42:12.027  INFO 3429 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 9000 (http)"},
                {message: "2017-08-21 14:42:12.027  INFO 3429 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 9000 (http)"}
            ]
        };
    }

    addMessage() {
        this.endpoint.logs.push(new LogMessages("2017-08-21 14:42:12.027  INFO 3429 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 9000 (http)111"));
    }

    ngOnInit(): void {
        this.scrollToBottom();
    }

    ngAfterViewChecked(): void {
        this.scrollToBottom();
    }

    scrollToBottom(): void {
        try {
            this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
        } catch (err) {
        }
    }
}
