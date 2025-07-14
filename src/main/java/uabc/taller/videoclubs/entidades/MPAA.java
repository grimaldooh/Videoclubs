package uabc.taller.videoclubs.entidades;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MPAA {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    @JsonValue
    private String mpaa;

    @Override
    public String toString() {
        return this.mpaa;
    }
}
