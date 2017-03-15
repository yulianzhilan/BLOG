package service.impl;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.servlet.DispatcherServlet;
import service.ConfigService;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by scott on 2017/3/15.
 */
public class ConfigServiceImpl implements ConfigService {
    public static String _TOKEN_KEY;
    static {
        Properties prop = new Properties();

        try {
            ClassPathResource resource = new ClassPathResource("env.properties");
            prop = PropertiesLoaderUtils.loadProperties(resource);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Could not load 'env.properties': " + ex.getMessage());
        }

        _TOKEN_KEY = prop.getProperty("_TOKEN_KEY");
    }

    @Override
    public String getConfig(String key) {
        return _TOKEN_KEY;
    }
}
