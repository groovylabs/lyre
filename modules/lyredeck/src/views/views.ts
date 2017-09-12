import {FilterBar} from "./components/filter-bar/filter-bar";
import {Requester} from "./components/requester/requester";
import {EndpointInfo} from "./components/endpoint-info/endpoint-info";
import {EndpointLog} from "./components/endpoint-log/endpoint-log";

import {Dashboard} from './contexts/dashboard/dashboard';
import {Endpoints} from './contexts/endpoints/endpoints';
import {Settings} from './contexts/settings/settings';

export function views() {
    return [
        Dashboard,
        Endpoints,
        Settings,
        FilterBar,
        Requester,
        EndpointInfo,
        EndpointLog
    ];
}
