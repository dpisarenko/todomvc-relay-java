package maven;

import graphql.schema.*;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * Created by pisarenko on 11.01.2017.
 */
public class MavenSchema {
    public static final String GROUP_ARGUMENT = "group";
    public static final String NAME_ARGUMENT = "name";
    public static final GraphQLObjectType QUERY_RESULT_TYPE = createQueryResultType();

    private final GraphQLSchema schema;

    public MavenSchema() {
        final ArtifactDataFetcher dataFetcher = new ArtifactDataFetcher();
        final GraphQLArgument groupArg = newArgument().name(GROUP_ARGUMENT).type(GraphQLString).build();
        final GraphQLArgument nameArg = newArgument().name(NAME_ARGUMENT).type(GraphQLString).build();

        final GraphQLFieldDefinition allArtifacts = newFieldDefinition()
                .name("allArtifacts")
                .type(new GraphQLList(QUERY_RESULT_TYPE))
                .argument(groupArg)
                .argument(nameArg)
                .dataFetcher(dataFetcher)
                .build();

        final GraphQLObjectType rootQuery = newObject().name("rootQuery").field(allArtifacts).build();
        schema = GraphQLSchema.newSchema().query(rootQuery).build();
    }
    private static GraphQLObjectType createQueryResultType() {
        final GraphQLFieldDefinition groupField = newFieldDefinition().name("group").type(GraphQLString).build();
        final GraphQLFieldDefinition nameField = newFieldDefinition().name("name").type(GraphQLString).build();
        final GraphQLFieldDefinition versionField = newFieldDefinition().name("version").type(GraphQLString).build();
        return newObject().name("queryResult")
                .field(groupField)
                .field(nameField)
                .field(versionField)
                .build();
    }

    public GraphQLSchema getSchema() {
        return schema;
    }
}
