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
    MdFormFieldModule,
    MdTableModule,
    MdPaginatorModule, MdGridListModule
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
        MdFormFieldModule,
        MdTableModule,
        MdPaginatorModule
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
        MdFormFieldModule,
        MdTableModule,
        MdPaginatorModule
    ],
})
export class Modules {
}
