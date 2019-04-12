package DTO;

import java.io.Serializable;
import java.time.LocalTime;

public class SearchDTO implements Serializable {
    String obiectiv;
    LocalTime start;
    LocalTime stop;

    public SearchDTO(String obiectiv, LocalTime start, LocalTime stop) {
        this.obiectiv = obiectiv;
        this.start = start;
        this.stop = stop;
    }

    public String getObiectiv() {
        return obiectiv;
    }

    public void setObiectiv(String obiectiv) {
        this.obiectiv = obiectiv;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getStop() {
        return stop;
    }

    public void setStop(LocalTime stop) {
        this.stop = stop;
    }
}
