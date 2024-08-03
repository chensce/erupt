package xyz.erupt.core.config;

import com.google.gson.*;
import lombok.Getter;
import xyz.erupt.core.util.DateUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author YuePeng
 * date 2021/3/1 13:03
 */
public class GsonFactory {

    @Getter
    private final static GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat(DateUtil.DATE_TIME)
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context)
                    -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(DateUtil.DATE_TIME))))
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context)
                    -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(DateUtil.DATE))))
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext)
                    -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(DateUtil.DATE_TIME)))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext)
                    -> LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(DateUtil.DATE)))
            .registerTypeAdapter(Long.class, (JsonSerializer<Long>) (src, type, jsonSerializationContext) -> {
                if (src > Integer.MAX_VALUE || src < Integer.MIN_VALUE) {
                    return new JsonPrimitive((src).toString());
                } else {
                    return new JsonPrimitive(src);
                }
            })
            .registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, type, jsonSerializationContext) -> {
                if (src > Integer.MAX_VALUE || src < Integer.MIN_VALUE) {
                    return new JsonPrimitive((src).toString());
                } else {
                    return new JsonPrimitive(src);
                }
            })
            .registerTypeAdapter(BigDecimal.class, (JsonSerializer<BigDecimal>) (src, type, jsonSerializationContext) -> {
                if (src.longValue() > Integer.MAX_VALUE || src.longValue() < Integer.MIN_VALUE) {
                    return new JsonPrimitive((src).toString());
                } else {
                    return new JsonPrimitive(src);
                }
            })
            .serializeNulls().setExclusionStrategies(new EruptGsonExclusionStrategies());

    @Getter
    private static final Gson gson = gsonBuilder.create();

    private GsonFactory() {
    }
}
