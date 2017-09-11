import {FilterBar} from "./components/filter-bar/filter-bar";

import {Dashboard} from './contexts/dashboard/dashboard';
import {Endpoints} from './contexts/endpoints/endpoints';
import {Settings} from './contexts/settings/settings';
import {Requester} from "./components/requester/requester";
import {EndpointInfo} from "./components/endpoint-info/endpoint-info";

export function views() {
    return [
        Dashboard,
        Endpoints,
        Settings,
        FilterBar,
        Requester,
        EndpointInfo
    ];
}
