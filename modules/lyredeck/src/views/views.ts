import {FilterBar} from "./components/filter-bar/filter-bar";
import {EndpointContent} from "./components/endpoint-content/endpoint-content";

import {Dashboard} from './contexts/dashboard/dashboard';
import {Endpoints} from './contexts/endpoints/endpoints';
import {Settings} from './contexts/settings/settings';

export function views() {
    return [
        Dashboard,
        Endpoints,
        Settings,
        FilterBar,
        EndpointContent
    ];
}
