package maven;

import graphql.ExecutionResult;
import graphql.GraphQL;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.fest.assertions.api.Assertions.*;

/**
 * Created by pisarenko on 17.01.2017.
 */
public class MavenSchemaTests {
    @Test
    public void test() throws IOException {
        // Prepare
        final MavenSchema schema = new MavenSchema();
        final GraphQL graphql = new GraphQL(schema.getSchema());
        final String query = FileUtils.readFileToString(
                new File("src/test/resources/query1.txt"),
                "UTF-8"
        );
        // Run method under test
        final ExecutionResult executionResult = graphql.execute(query);
        // Verify
        assertThat(executionResult).isNotNull();
        assertThat(executionResult.getErrors()).isEmpty();
    }
}
