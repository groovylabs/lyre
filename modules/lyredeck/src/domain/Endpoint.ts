import {LogMessages} from "./LogMessages";

export class Endpoint {

    public method       : string;
    public path         : string;
    public logs         : LogMessages[];

    constructor(method: string, path: string, logs: LogMessages[]) {
        this.method = method;
        this.path = path;
        this.logs = logs;
    }

}
