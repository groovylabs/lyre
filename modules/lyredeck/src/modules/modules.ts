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
    MdPaginatorModule,
    MdGridListModule,
    MdDialogModule
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
        MdPaginatorModule,
        MdDialogModule
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
        MdPaginatorModule,
        MdDialogModule
    ],
})
export class Modules {
}
