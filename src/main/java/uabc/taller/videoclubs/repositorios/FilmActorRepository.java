package uabc.taller.videoclubs.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import uabc.taller.videoclubs.entidades.FilmActor;
import uabc.taller.videoclubs.entidades.FilmActorId;

public interface FilmActorRepository extends JpaRepository<FilmActor, FilmActorId>{

}
