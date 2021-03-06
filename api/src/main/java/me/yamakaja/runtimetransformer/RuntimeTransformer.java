package me.yamakaja.runtimetransformer;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Yamakaja on 19.05.17.
 */
public class RuntimeTransformer {

    public RuntimeTransformer(Class<?>... transformers) {
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();

        try {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);

            File file = new File(new File(System.getProperty("java.home")).getParent(), "lib/tools.jar");
            if (!file.exists())
                throw new RuntimeException("Not running with JDK!");

            method.invoke(urlClassLoader, file.toURI().toURL());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        TransformerUtils.attachAgent(TransformerUtils.saveAgentJar(), transformers);
    }

}
