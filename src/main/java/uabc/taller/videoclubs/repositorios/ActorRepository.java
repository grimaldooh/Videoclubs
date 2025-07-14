package uabc.taller.videoclubs.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import uabc.taller.videoclubs.entidades.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer>{

	Page<Actor> findAllByFirstNameContainsOrLastNameContains(String term, String term2, Pageable pageable);

}
