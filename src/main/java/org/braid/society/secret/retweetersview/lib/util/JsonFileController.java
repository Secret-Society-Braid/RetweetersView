package org.braid.society.secret.retweetersview.lib.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for read/write JSON file.
 *
 * @author Ranfa
 * @apiNote each value will be wrapped into {@link Optional}. users must treat a case that value
 * doesn't exist.
 * @since 1.0.0
 */
@Slf4j
public class JsonFileController {

  /**
   * for the purpose to reduce usage of resources.
   */
  protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final TypeReference<Map<String, Object>> TYPE_REFERENCE = new TypeReference<>() {
  };

  /**
   * instance variable for containing raw {@link JsonNode} data.
   */
  protected JsonNode rawData;

  /**
   * creates the instance for specified json data.
   *
   * @param rawData rawData for specify.
   */
  private JsonFileController(JsonNode rawData) {
    this.rawData = rawData;
    log.debug("data setting complete.");
  }

  /**
   * create the instance from reading json file and traverse {@link JsonNode}.
   *
   * @param fileName file name to read.
   * @throws IOException If failed to read due to missing access permission, or that file does not
   *                     exist, or so.
   */
  private JsonFileController(String fileName) throws IOException {
    this(OBJECT_MAPPER.readTree(Paths.get(fileName).toFile()));
  }

  /**
   * Factory method for getting {@link JsonFileController} instance.
   *
   * @param fileName File name to read.
   * @return new instance with specified file name.
   * @throws IOException if failed to read due to missing access permission or that file does not
   *                     exist or so.
   */
  public static JsonFileController getInstance(String fileName) throws IOException {
    log.debug("Specified file name: {}", fileName);
    if (Strings.isNullOrEmpty(fileName)) {
      throw new IllegalArgumentException("File name cannot be set to null");
    }
    return new JsonFileController(fileName);
  }

  /**
   * Factory method for getting {@link JsonFileController} instance.
   *
   * @param rawData specified data to traverse.
   * @return new instance with specified json data.
   */
  public static JsonFileController getInstance(JsonNode rawData) {
    log.debug("Specified jsonNode data: {}", rawData);
    if (rawData.isNull() || rawData.isEmpty()) {
      throw new IllegalArgumentException("JsonNode data cannot be set to null");
    }
    return new JsonFileController(rawData);
  }

  /**
   * Factory method for getting {@link JsonFileController} instance.
   *
   * @param url specified {@link URL} object to traverse.
   * @return new instance with specified json data.
   * @throws IOException if there is an error while processing URL.
   */
  public static JsonFileController getInstance(URL url) throws IOException {
    log.debug("Specified url data: {}", url);
    if (Objects.isNull(url)) {
      throw new IllegalArgumentException("URL data cannot be set to null");
    }
    return new JsonFileController(OBJECT_MAPPER.readTree(url));
  }

  /**
   * write down the specified {@link Map} data to json file.
   *
   * @param fileName file name to write.
   * @param data     {@link Map} data to write.
   * @throws IOException if there is an error while processing {@link Map} data.
   */
  public static <T> void writeToFile(String fileName, Map<String, T> data) throws IOException {
    OBJECT_MAPPER.writeValue(Paths.get(fileName).toFile(), data);
  }

  /**
   * write down the specified {@link JsonNode} data to json file.
   *
   * @param fileName file name to write.
   * @param data     {@link JsonNode} data to write.
   * @throws IOException if there is an error while processing {@link JsonNode} data.
   */
  public static void writeToFile(String fileName, JsonNode data) throws IOException {
    OBJECT_MAPPER.writeValue(Paths.get(fileName).toFile(), data);
  }

  /**
   * traverse the JSON, and check whether it contains specified key or not.
   *
   * @param key Key String you want to check.
   * @return {@code true} if json has key, {@code false} otherwise.
   */
  public boolean isKeyExists(String key) {
    if (Strings.isNullOrEmpty(key)) {
      throw new IllegalArgumentException("key must be nonnull.");
    }
    Iterator<String> keyIterator = rawData.fieldNames();
    while (keyIterator.hasNext()) {
      String keyString = keyIterator.next();
      if (keyString.equals(key)) {
        return true;
      }
    }
    return false;
  }

  /**
   * get the value associated with parameter.
   *
   * @param key key that you want to get the value.
   * @return value that associated with specified key.
   */
  public Optional<Object> getValue(String key) {
    if (!isKeyExists(key)) {
      return Optional.empty();
    }
    return Optional.ofNullable(rawData.get(key));
  }

  /**
   * Converts the {@link JsonNode} to {@link Map}.
   *
   * @return {@link Map} instance from {@link JsonNode}
   * @throws IOException if there is an error while processing {@link JsonNode}.
   */
  public Map<String, Object> convertToMap() throws IOException {
    return OBJECT_MAPPER.readValue(rawData.traverse(), TYPE_REFERENCE);
  }
}
