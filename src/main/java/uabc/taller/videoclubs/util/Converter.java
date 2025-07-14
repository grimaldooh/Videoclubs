package uabc.taller.videoclubs.util;

public abstract class Converter<E, D> {

    public abstract E fromDTO(D d);

    public abstract D fromEntity(E e);
}
