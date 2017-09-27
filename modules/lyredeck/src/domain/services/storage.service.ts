import {Injectable} from "@angular/core";

const storage = localStorage;

@Injectable()
export class StorageService {

    getItem(key: string) {
        return storage.getItem(key);
    }

    setItem(key: string, value: string) {
        storage.setItem(key, value);
    }

}
