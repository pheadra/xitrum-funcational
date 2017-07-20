package io.haru.funcational.xitrum.repository;

import io.haru.funcational.xitrum.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {
    Mono<Person> getPerson(int id);
    Flux<Person> allPeople();
    Mono<Void> savePerson(Mono<Person> person);
}