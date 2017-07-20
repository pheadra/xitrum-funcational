package io.haru.funcational.xitrum.handler;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.haru.funcational.xitrum.domain.Person;
import io.haru.funcational.xitrum.repository.PersonRepository;
import reactor.core.publisher.Mono;

public class PersonHandler {
    private final PersonRepository repository;


    public PersonHandler(PersonRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> allPeople(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(repository.allPeople(), Person.class);
    }

    public Mono<ServerResponse> savePerson(ServerRequest request) {
        Mono<Person> personMono = request.bodyToMono(Person.class);
        Mono<Void> result = repository.savePerson(personMono);
        return ServerResponse.ok().build(result);
    }

    public Mono<ServerResponse> getPerson(ServerRequest request) {
        int personId = Integer.valueOf(request.pathVariable("id"));

        Mono<Person> personMono = repository.getPerson(personId);

        return personMono.flatMap(person -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(personMono, Person.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }
}
