import {ApolloServer} from '@apollo/server';
import {ApolloGateway, IntrospectAndCompose, RemoteGraphQLDataSource, ServiceEndpointDefinition} from '@apollo/gateway';
import {startStandaloneServer} from '@apollo/server/standalone';

const gateway = (subgraphs: ServiceEndpointDefinition[]) =>
    new ApolloGateway({
        supergraphSdl: new IntrospectAndCompose({
            subgraphs
        }),
        buildService({ name, url }) {
            return new RemoteGraphQLDataSource({
                url,
                async willSendRequest({ request, context }) {
                    const token = context.authorization ?? '';
                    request.http?.headers.set('authorization', token);
                }
            });
        }
    });
const gatewayServer = new ApolloServer({
    gateway: gateway([
        { name: 'todos', url: 'http://localhost:8081/graphql' },
        { name: 'users', url: 'http://localhost:8082/graphql' }
    ])
});

const { url } = await startStandaloneServer(gatewayServer, {
    listen: { port: 4000 }
});

console.log(`🚀  Server ready at ${url}`);
