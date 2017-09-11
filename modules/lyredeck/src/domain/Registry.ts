import {StompService} from 'ng2-stomp-service';

export class Registry {

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
            console.log('connected');

            //subscribe
            this.subscription = stomp.subscribe('/registry/bundle', this.getBundle);

        });

    }

    //response
    public getBundle = (data) => {
        console.log(data);
    }

}
