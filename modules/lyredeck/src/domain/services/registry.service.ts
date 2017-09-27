import {StompService} from 'ng2-stomp-service';
import {Injectable} from "@angular/core";

@Injectable()
export class RegistryService {

    private subscription: any;

    constructor(stomp: StompService) {

        //configuration
        stomp.configure({
            host: 'http://localhost:8234/apix',
            debug: true,
            queue: {'topic': false}
        });

        //start connection
        stomp.startConnect().then(() => {
            stomp.done('topic');
        });

        stomp.after('topic').then(() => {

            //subscribe
            this.subscription = stomp.subscribe('/registry/bundle', this.getBundle);

            stomp.subscribe('/registry/log/2c5ae7def9b71ddb2de8ca896a340b9648a75184948fd343ad6d4713b324dd6b', this.getLog);

            console.log('after');
            
        });

    }

    //response
    public getLog = (data) => {
        console.log(data);
    };

    //response
    public getBundle = (data) => {
        console.log(data);
    };

}
