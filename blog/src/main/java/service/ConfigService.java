package service;

/**
 * Created by scott on 2017/3/15.
 */
//@Service
public interface ConfigService {
    String getConfig(String key);
    String encode(String message);
    String decode(String message);
}
