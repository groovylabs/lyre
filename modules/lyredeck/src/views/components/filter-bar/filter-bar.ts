import {Component, ViewChild} from '@angular/core';

import {DataSource} from '@angular/cdk/collections';
import {MdPaginator} from '@angular/material';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/observable/merge';
import 'rxjs/add/operator/map';


@Component({
    selector: 'filter-bar',
    templateUrl: './filter-bar.html',
    styleUrls: ['./filter-bar.scss']
})

export class FilterBar {

    displayedColumns = ['http', 'path', 'status'];
    exampleDatabase = new ExampleDatabase();
    dataSource: ExampleDataSource | null;

    @ViewChild(MdPaginator) paginator: MdPaginator;

    ngOnInit() {
        this.dataSource = new ExampleDataSource(this.exampleDatabase, this.paginator);
    }
}

export interface Endpoint {
    http: string;
    path: string;
    status: string;
}

/** An example database that the data source uses to retrieve data for the table. */
export class ExampleDatabase {
    /** Stream that emits whenever the data has been modified. */
    dataChange: BehaviorSubject<Endpoint[]> = new BehaviorSubject<Endpoint[]>([]);
    get data(): Endpoint[] { return this.dataChange.value; }

    constructor() {
        for (let i = 0; i < 25; i++) { this.addUser(); }
    }

    /** Adds a new user to the database. */
    addUser() {
        const copiedData = this.data.slice();
        copiedData.push(this.createNewEndpoint());
        this.dataChange.next(copiedData);
    }

    /** Builds and returns a new User. */
    private createNewEndpoint() {
        return {
            http: 'GET',
            path: '/path/lyre',
            status: (200 + Math.round(Math.random() * 100)).toString()
        };
    }
}

export class ExampleDataSource extends DataSource<any> {
    constructor(private _exampleDatabase: ExampleDatabase, private _paginator: MdPaginator) {
        super();
    }

    /** Connect function called by the table to retrieve one stream containing the data to render. */
    connect(): Observable<Endpoint[]> {
        const displayDataChanges = [
            this._exampleDatabase.dataChange,
            this._paginator.page,
        ];

        return Observable.merge(...displayDataChanges).map(() => {
            const data = this._exampleDatabase.data.slice();

            // Grab the page's slice of data.
            const startIndex = this._paginator.pageIndex * this._paginator.pageSize;
            return data.splice(startIndex, this._paginator.pageSize);
        });
    }

    disconnect() {}
}
