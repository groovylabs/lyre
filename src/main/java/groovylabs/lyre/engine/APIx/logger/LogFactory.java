package groovylabs.lyre.engine.APIx.logger;

import groovylabs.lyre.domain.Log;
import org.springframework.beans.factory.FactoryBean;

public class LogFactory implements FactoryBean<Log> {

    @SuppressWarnings("unchecked")
    public Log logger(Class[] clz, Object... parameters) {
        Log logInstance = this.getObject();
        logInstance.setTarget(clz[0].cast(parameters[0]));
        logInstance.setParameters(parameters);
        logInstance.build();
        return logInstance;
    }

    @Override
    public Log getObject() {
        return new Log();
    }

    @Override
    public Class<?> getObjectType() {
        return Log.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
