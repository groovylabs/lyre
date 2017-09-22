package groovylabs.lyre.domain.factories;

import groovylabs.lyre.domain.Bundle;
import org.springframework.beans.factory.FactoryBean;

public class BundleFactory implements FactoryBean<Bundle> {

    @Override
    public Bundle getObject() {
        return new Bundle();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
