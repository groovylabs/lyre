import {NgModule} from '@angular/core';

import {
    MdButtonModule,
    MdCheckboxModule,
    MdToolbarModule,
    MdMenuModule,
    MdIconModule,
    MdCardModule,
    MdSidenavModule,
    MdListModule,
    MdInputModule,
    MdFormFieldModule
} from '@angular/material';

@NgModule({
    imports: [
        MdButtonModule,
        MdCheckboxModule,
        MdToolbarModule,
        MdMenuModule,
        MdIconModule,
        MdCardModule,
        MdSidenavModule,
        MdListModule,
        MdInputModule,
        MdFormFieldModule
    ],
    exports: [
        MdButtonModule,
        MdCheckboxModule,
        MdToolbarModule,
        MdMenuModule,
        MdIconModule,
        MdCardModule,
        MdSidenavModule,
        MdListModule,
        MdInputModule,
        MdFormFieldModule
    ],
})
export class Modules {
}
