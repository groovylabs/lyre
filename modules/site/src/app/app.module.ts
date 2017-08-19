import {BrowserModule} from '@angular/platform-browser';
import {ErrorHandler, NgModule} from '@angular/core';
import {IonicApp, IonicErrorHandler, IonicModule} from 'ionic-angular';
import {SplashScreen} from '@ionic-native/splash-screen';
import {StatusBar} from '@ionic-native/status-bar';

import {MyApp} from './app.component';
import {DashboardPage} from '../pages/dashboard/dashboard';
import {EndpointsPage} from '../pages/endpoints/endpoints';
import {LogsPage} from '../pages/logs/logs';
import {ScenariosPage} from '../pages/scenarios/scenarios';
import {SettingsPage} from '../pages/settings/settings';

@NgModule({
    declarations: [
        MyApp,
        DashboardPage,
        EndpointsPage,
        LogsPage,
        ScenariosPage,
        SettingsPage
    ],
    imports: [
        BrowserModule,
        IonicModule.forRoot(MyApp)
    ],
    bootstrap: [IonicApp],
    entryComponents: [
        MyApp,
        DashboardPage,
        EndpointsPage,
        LogsPage,
        ScenariosPage,
        SettingsPage
    ],
    providers: [
        StatusBar,
        SplashScreen,
        {provide: ErrorHandler, useClass: IonicErrorHandler}
    ]
})
export class AppModule {
}
