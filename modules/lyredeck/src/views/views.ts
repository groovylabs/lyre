import {FilterBar} from "./components/filter-bar/filter-bar";
import {EndpointActions} from "./components/endpoint-viewer/endpoint-actions/endpoint-actions";
import {EndpointInfo} from "./components/endpoint-viewer/endpoint-info/endpoint-info";
import {EndpointLog} from "./components/endpoint-viewer/endpoint-log/endpoint-log";
import {EndpointViewer} from "./components/endpoint-viewer/endpoint-viewer";

import {Dashboard} from './contexts/dashboard/dashboard';
import {Endpoints} from './contexts/endpoints/endpoints';
import {Settings} from './contexts/settings/settings';

export function views() {
    return [
        Dashboard,
        Endpoints,
        Settings,
        FilterBar,
        EndpointViewer,
        EndpointActions,
        EndpointInfo,
        EndpointLog
    ];
}
