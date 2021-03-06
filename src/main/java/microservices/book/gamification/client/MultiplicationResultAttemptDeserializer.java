package microservices.book.gamification.client;

import java.io.IOException;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import microservices.book.gamification.client.dto.MultiplicationResultAttempt;

/**
 * This class implements the {@link JsonDeserialize} annotation is to instruct
 * our {@link RestTemplate} ’s message converter to use a special deserializer
 * to read the JSON data. We need this since the JSON structure we’ll receive
 * doesn’t match with our Java class (since it’s matching the original
 * MultiplicationResultAttempt in the multiplication microservice), so the
 * default deserializer won’t work. We’ll cover that implementation in the
 * following subsection .
 * 
 * @author sabaja
 *
 */
public class MultiplicationResultAttemptDeserializer extends JsonDeserializer<MultiplicationResultAttempt> {

    @Override
    public MultiplicationResultAttempt deserialize(JsonParser jsonParser,
                                                   DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        return new MultiplicationResultAttempt(node.get("user").get("alias").asText(),
                node.get("multiplication").get("factorA").asInt(),
                node.get("multiplication").get("factorB").asInt(),
                node.get("resultAttempt").asInt(),
                node.get("correct").asBoolean());
    }
}
