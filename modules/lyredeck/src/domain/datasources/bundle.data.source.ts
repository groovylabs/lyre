import {DataSource} from "@angular/cdk/collections";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {MdPaginator} from "@angular/material";
import {Endpoint} from "../Endpoint";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {Bundle} from "../Bundle";

export class BundleDataSource extends DataSource<any> {

    _filterChange = new BehaviorSubject('');

    get filter(): string {
        return this._filterChange.value;
    }

    set filter(filter: string) {
        this._filterChange.next(filter);
    }

    constructor(private _service: BundleService, private _paginator: MdPaginator) {
        super();
    }

    connect(): Observable<Endpoint[]> {
        const displayDataChanges = [
            this._service.dataChange,
            this._filterChange,
            this._paginator.page
        ];

        return Observable.merge(...displayDataChanges).map(() => {

            const data = this._service.data.endpoints.slice().filter((item: Endpoint) => {

                // TODO FILTER BY OTHER OPTIONS

                let searchStr = (item.path).toLowerCase();
                return searchStr.indexOf(this.filter.toLowerCase()) != -1;
            });

            // Grab the page's slice of data.
            const startIndex = this._paginator.pageIndex * this._paginator.pageSize;
            return data.splice(startIndex, this._paginator.pageSize);
        });
    }

    disconnect() {
    }
}

export class BundleService {

    dataChange: BehaviorSubject<Bundle> = new BehaviorSubject<Bundle>(new Bundle());

    get data(): Bundle {
        return this.dataChange.value;
    }

    constructor(private http: HttpClient) {
    }

    getBundle() {

        this.http.get('http://localhost:8234/lyre/bundle').subscribe((data: Bundle) => {
            console.log(data);
            this.dataChange.next(data);
        });
    }
}
