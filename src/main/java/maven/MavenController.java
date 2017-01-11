package maven;

import graphql.ExecutionResult;
import graphql.GraphQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by pisarenko on 11.01.2017.
 */
@Controller
@EnableAutoConfiguration
public class MavenController {
    private final MavenSchema schema = new MavenSchema();
    private final GraphQL graphql = new GraphQL(schema.getSchema());
    private static final Logger log = LoggerFactory.getLogger(MavenController.class);

    @CrossOrigin(origins = "http://localhost:8888", allowedHeaders = {"", ""})
    @RequestMapping(value = "/graphql", method = RequestMethod.OPTIONS, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object executeOperation(@RequestBody Map body) {
        log.error("body: " + body);
        final String query = (String) body.get("query");
        final Map<String, Object> variables = (Map<String, Object>) body.get("variables");
        final ExecutionResult executionResult = graphql.execute(query, (Object) null, variables);
        final Map<String, Object> result = new LinkedHashMap<>();
        if (executionResult.getErrors().size() > 0) {
            result.put("errors", executionResult.getErrors());
            log.error("Errors: {}", executionResult.getErrors());
        }
        log.error("data: " + executionResult.getData());
        result.put("data", executionResult.getData());
        return result;
    }
}
