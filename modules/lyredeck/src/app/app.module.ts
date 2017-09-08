import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {AppComponent} from './app.component';

import {Modules} from '../modules/modules';

import {Dashboard} from '../views/dashboard/dashboard';
import {Endpoints} from '../views/endpoints/endpoints';
import {Settings} from '../views/settings/settings';


@NgModule({
    declarations: [
        AppComponent,
        Dashboard,
        Endpoints,
        Settings
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        BrowserAnimationsModule,
        Modules
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
