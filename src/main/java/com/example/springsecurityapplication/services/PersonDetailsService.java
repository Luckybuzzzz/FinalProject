package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.repositories.PersonRepository;
import com.example.springsecurityapplication.security.PersonDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;
    private final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Person> person = personRepository.findByLogin(username);

        if (person.isEmpty()) {

            throw new UsernameNotFoundException("Пользователь не найден");
        }
        logger.info("Пользователь: {}", person.get().getLogin());
        logger.info("Роль: {}", person.get().getRole());
        return new PersonDetails(person.get());
    }
}
