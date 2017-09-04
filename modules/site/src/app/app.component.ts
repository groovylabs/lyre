import {Component} from '@angular/core';
import {Platform} from 'ionic-angular';
import {StatusBar} from '@ionic-native/status-bar';

import {DashboardPage} from '../pages/dashboard/dashboard';
import {EndpointsPage} from '../pages/endpoints/endpoints';
import {LogsPage} from '../pages/logs/logs';
import {ScenariosPage} from '../pages/scenarios/scenarios';
import {SettingsPage} from '../pages/settings/settings';

@Component({
    templateUrl: 'app.html'
})
export class MyApp {
    rootPage: any = EndpointsPage;

    constructor(platform: Platform, statusBar: StatusBar) {
        platform.ready().then(() => {
            statusBar.styleDefault();
        });
    }

    route(page: String): void {
        this.rootPage = this.page(page);
    }

    private page(page: String): Component {
        switch (page) {
            case 'dashboard':
                return DashboardPage;
            case 'endpoints':
                return EndpointsPage;
            case 'logs':
                return LogsPage;
            case 'scenarios':
                return ScenariosPage;
            case 'settings':
                return SettingsPage;
            default:
                return DashboardPage;

        }
    }
}
