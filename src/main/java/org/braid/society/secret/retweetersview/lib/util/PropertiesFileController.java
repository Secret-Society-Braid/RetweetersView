package org.braid.society.secret.retweetersview.lib.util;

import com.google.common.base.Strings;
import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;

@Slf4j
@Getter
@Setter
@EqualsAndHashCode
public class PropertiesFileController {

    private String fileName;
    private Properties properties;

    public PropertiesFileController(String fileName) {
        this.fileName = fileName;
        log.debug("file name has been set to {}", fileName);
    }

    public PropertiesFileController(Properties properties) {
        this.properties = properties;
        log.debug("properties has been set to {}", properties);
    }

    public void loadPropertiesFromResource() {
        if(Strings.isNullOrEmpty(fileName)) {
            log.warn("file name must not be null or empty.");
            return;
        }
        URL url = Resources.getResource(fileName);
        Properties prop = new Properties();
        final ByteSource byteSource = Resources.asByteSource(url);
        try (InputStream is = byteSource.openBufferedStream()) {
            prop.load(is);
        } catch (IOException e) {
            log.error("failed to load properties from {}", fileName, e);
        }
        this.properties = prop;
    }

    public void loadPropertiesFromLocal() {
        if(Strings.isNullOrEmpty(fileName)) {
            log.warn("file name must not be null or empty.");
            return;
        }
        Properties prop = new Properties();
        try(InputStream is = new BufferedInputStream(new FileInputStream(Paths.get(fileName).toFile()))) {
            prop.load(is);
        } catch (IOException e) {
            log.error("Exception while reading local file: {}", fileName, e);
        }
        this.properties = prop;
    }

    public void write() {
        if(properties == null || properties.isEmpty()) {
            log.warn("properties must not be null or empty.");
            return;
        }
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName))) {
            properties.store(bos, "properties");
        } catch (IOException e) {
            log.error("failed to write properties to {}", fileName, e);
        }
    }
}
