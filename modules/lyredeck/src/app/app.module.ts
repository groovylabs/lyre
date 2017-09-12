import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {StompService} from 'ng2-stomp-service';

import {AppComponent} from './app.component';

import {Modules} from '../modules/modules';

import {views} from "../views/views";

const storage = localStorage;

@NgModule({
    declarations: [
        AppComponent,
        views()
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        HttpClientModule,
        BrowserAnimationsModule,
        Modules
    ],
    entryComponents: [
        views()
    ],
    providers: [StompService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
