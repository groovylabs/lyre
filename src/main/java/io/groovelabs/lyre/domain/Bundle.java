package io.groovelabs.lyre.domain;

import java.util.ArrayList;
import java.util.List;

public class Bundle {

    List<Endpoint> list = new ArrayList<>();

    public Bundle() {

    }

    public List<Endpoint> getList() {
        return list;
    }

    public void add(Endpoint endpoint) {
        if (endpoint != null)
            list.add(endpoint);
    }

}
