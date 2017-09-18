import {Component, ViewChild, ElementRef, EventEmitter, Output} from '@angular/core';
import {HttpClient} from "@angular/common/http";

import {MdPaginator} from '@angular/material';
import {BundleDataSource, BundleService} from "../../../domain/BundleDataSource";

import 'rxjs/add/operator/startWith';
import 'rxjs/add/observable/merge';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/observable/fromEvent';
import { Endpoint } from "../../../domain/Endpoint";

@Component({
    selector: 'filter-bar',
    templateUrl: './filter-bar.html',
    styleUrls: ['./filter-bar.scss']
})

export class FilterBar {

    displayedColumns = ['method', 'path'];
    dataProvider;
    dataSource: BundleDataSource | null;

    @ViewChild('filter') filter: ElementRef;

    @ViewChild(MdPaginator) paginator: MdPaginator;

    @Output('selectedEndpoint') event = new EventEmitter();

    constructor(private http: HttpClient) {
        this.dataProvider = new BundleService(this.http);
    }

    ngOnInit() {
        this.dataProvider.getBundle();
        this.dataSource = new BundleDataSource(this.dataProvider, this.paginator);
    }

    search() {

        console.log(this.dataSource);

        if (!this.dataSource) {
            return;
        }
        this.dataSource.filter = this.filter.nativeElement.value;
    }

    clear(): void {
        this.dataSource.filter = this.filter.nativeElement.value = '';
    }

    selectedEndpoint(endpoint: Endpoint) {
        this.event.emit(endpoint);
    }

}
