import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {AppComponent} from './app.component';

import {Modules} from '../modules/modules';

import {views} from "../views/views";

@NgModule({
    declarations: [
        AppComponent,
        views()
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        BrowserAnimationsModule,
        Modules
    ],
    entryComponents: [
        views()
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
