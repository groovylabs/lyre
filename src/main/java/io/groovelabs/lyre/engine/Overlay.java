package io.groovelabs.lyre.engine;

public abstract class Overlay<T> {

    private T layer;

    public Overlay(T layer) {
        setLayer(layer);
    }

    public T overlay() {
        return layer;
    }

    public T getLayer() {
        return layer;
    }

    public void setLayer(T layer) {
        this.layer = layer;
    }
}
