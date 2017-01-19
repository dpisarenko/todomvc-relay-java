package maven;

import com.google.common.collect.Sets;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
                new File("src/test/resources/query01.txt"),
                "UTF-8"
        );
        // Run method under test
        final ExecutionResult executionResult = graphql.execute(query);
        // Verify
        assertThat(executionResult).isNotNull();
        assertThat(executionResult.getErrors()).isEmpty();
        final List<Map> allArtifacts = (List) executionResult.getData().get("allArtifacts");
        assertThat(allArtifacts.size()).isEqualTo(6);

        allArtifacts.stream().forEach(x -> {
            assertThat(x.get("group")).isEqualTo("com.graphql-java");
            assertThat(x.get("name")).isEqualTo("graphql-java");
        });

        final Set<String> actualVersions = allArtifacts
                .stream()
                .map(x -> (String)x.get("version"))
                .collect(Collectors.toSet());
        final Set<String> expectedVersions = Sets.newHashSet("1.2",
                "1.3",
                "2.0.0",
                "2.1.0",
                "2.2.0",
                "2.3.0");
        assertThat(actualVersions.size()).isEqualTo(expectedVersions.size());
        assertThat(actualVersions.containsAll(expectedVersions)).isTrue();
    }
}
