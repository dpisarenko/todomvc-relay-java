package maven;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.version.Version;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static maven.MavenSchema.GROUP_ARGUMENT;
import static maven.MavenSchema.NAME_ARGUMENT;

/**
 * Created by pisarenko on 11.01.2017.
 */
public class ArtifactDataFetcher implements DataFetcher {
    public Object get(final DataFetchingEnvironment dataFetchingEnvironment) {
        final String name = (String) dataFetchingEnvironment.getArguments().get(NAME_ARGUMENT);
        final String group = (String) dataFetchingEnvironment.getArguments().get(GROUP_ARGUMENT);
        final List<Version> artifactVersions = findArtifacts(group, name);
        return artifactVersions.stream().map(av -> toGraphQL(group, name, av)).collect(Collectors.toList());
        //return new ArrayList<Object>();
    }

    private Object toGraphQL(final String group, final String name, final Version version) {
        final maven.Artifact artifact = new maven.Artifact();
        artifact.setGroup(group);
        artifact.setName(name);
        artifact.setVersion(version.toString());
        return artifact;
    }

    private List<Version> findArtifacts(final String group, final String name) {
        try
        {
            final RepositorySystem repoSystem = newRepositorySystem();
            final RepositorySystemSession session = newRepositorySystemSession( repoSystem );
            final Artifact artifact = new DefaultArtifact( String.format("%s:%s:[0,)", group, name) );
            final VersionRangeRequest rangeRequest = new VersionRangeRequest();
            rangeRequest.setArtifact( artifact );
            rangeRequest.setRepositories( newRepositories( repoSystem, session ) );
            final VersionRangeResult rangeResult = repoSystem.resolveVersionRange( session, rangeRequest );
            return rangeResult.getVersions();
        } catch (final VersionRangeResolutionException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
    private static RepositorySystem newRepositorySystem()
    {
        DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
        locator.addService( RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class );
        locator.addService( TransporterFactory.class, FileTransporterFactory.class );
        locator.addService( TransporterFactory.class, HttpTransporterFactory.class );

        return locator.getService( RepositorySystem.class );
    }
    public static DefaultRepositorySystemSession newRepositorySystemSession(RepositorySystem system )
    {
        DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();

        LocalRepository localRepo = new LocalRepository( "target/local-repo" );
        session.setLocalRepositoryManager( system.newLocalRepositoryManager( session, localRepo ) );

        session.setTransferListener( new ConsoleTransferListener() );
        session.setRepositoryListener( new ConsoleRepositoryListener() );

        // uncomment to generate dirty trees
        // session.setDependencyGraphTransformer( null );

        return session;
    }
    public static List<RemoteRepository> newRepositories( RepositorySystem system, RepositorySystemSession session )
    {
        return new ArrayList<>(Arrays.asList(newCentralRepository()));
    }
    private static RemoteRepository newCentralRepository()
    {
        return new RemoteRepository.Builder( "central", "default", "http://central.maven.org/maven2/" ).build();
    }
}
