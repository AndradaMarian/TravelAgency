package Services;


import java.time.LocalTime;
import Domain.*;
public interface IAgentieObserver {
    void bookingAdded(Iterable<Excursie> excursii);

}
