import {NgModule} from '@angular/core';

import {
    MdButtonModule,
    MdCheckboxModule,
    MdToolbarModule,
    MdMenuModule,
    MdIconModule,
    MdCardModule,
    MdSidenavModule
} from '@angular/material';

@NgModule({
    imports: [MdButtonModule, MdCheckboxModule, MdToolbarModule, MdMenuModule, MdIconModule, MdCardModule, MdSidenavModule],
    exports: [MdButtonModule, MdCheckboxModule, MdToolbarModule, MdMenuModule, MdIconModule, MdCardModule, MdSidenavModule],
})
export class Modules {
}
