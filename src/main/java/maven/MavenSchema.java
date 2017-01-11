package maven;

import graphql.schema.GraphQLSchema;

/**
 * Created by pisarenko on 11.01.2017.
 */
public class MavenSchema {
    private final GraphQLSchema schema;

    public MavenSchema() {
        schema = null;
    }

    public GraphQLSchema getSchema() {
        return schema;
    }
}
