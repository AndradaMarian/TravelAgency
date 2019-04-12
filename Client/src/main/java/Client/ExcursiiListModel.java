package Client;

import Domain.Excursie;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ExcursiiListModel extends AbstractListModel {
    List<Excursie>trips;

    public ExcursiiListModel() {
        trips=new ArrayList<>();

    }

    @Override
    public int getSize() {
        return trips.size();
    }

    @Override
    public Object getElementAt(int index) {
       return trips.get(index);
    }
}
