package io.haru.funcational.xitrum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.haru.funcational.xitrum.repository.DummyPersonRepository;
import io.haru.funcational.xitrum.handler.PersonHandler;
import io.haru.funcational.xitrum.repository.PersonRepository;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class RoutingConfiguration {
    @Bean
    public PersonRepository repository() {
        return new DummyPersonRepository();
    }

    @Bean
    public PersonHandler handler(PersonRepository repository) {
        return new PersonHandler(repository);
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(PersonHandler handler){
        return nest(path("/person"),
                nest(accept(MediaType.APPLICATION_JSON),
                        route(GET("/{id}"), handler::getPerson)
                        .andRoute(method(HttpMethod.GET), handler::allPeople)
                ).andRoute(POST("/").and(contentType(MediaType.APPLICATION_JSON)), handler::savePerson));
    }
}
