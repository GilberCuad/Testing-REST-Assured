package blass.academy.utils;

import tools.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonManager {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T parsearJson(String content, Class<T> clazz) {
        return mapper.readValue(content, clazz);
    }

    public static <T> List<T> parsearListaJson(String content, Class<T> clazz) {
        return mapper.readValue(
                content,
                mapper.getTypeFactory().constructCollectionType(List.class, clazz)
        );
    }
}