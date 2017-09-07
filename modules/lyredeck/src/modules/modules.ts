import {NgModule} from '@angular/core';

import {
    MdButtonModule,
    MdCheckboxModule,
    MdToolbarModule,
    MdMenuModule,
    MdIconModule,
    MdCardModule,
    MdSidenavModule,
    MdListModule
} from '@angular/material';

@NgModule({
    imports: [MdButtonModule, MdCheckboxModule, MdToolbarModule, MdMenuModule, MdIconModule, MdCardModule, MdSidenavModule, MdListModule],
    exports: [MdButtonModule, MdCheckboxModule, MdToolbarModule, MdMenuModule, MdIconModule, MdCardModule, MdSidenavModule, MdListModule],
})
export class Modules {
}
