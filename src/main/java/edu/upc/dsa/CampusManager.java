package edu.upc.dsa;

import edu.upc.dsa.exceptions.TrackNotFoundException;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PointOfInterest;
import edu.upc.dsa.models.Usuari;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public interface CampusManager {


    void addUser (String id, String name, String surname, String email, LocalDate birthDate);
    void addUser (Usuari u);

    List<Usuari> listUsersAlphabetically();
    Usuari getUsuari(String id);

    void addPointOfInterest(int x, int y, ElementType type);
    void addPointOfInterest(PointOfInterest p);
    PointOfInterest getPointOfInterest(int x, int y);


    void registerUserVisit(String userId, int x, int y);
    List<PointOfInterest> getUserVisitedPoints(String userId);
    List<Usuari> getUsersByPointOfInterest(int x, int y);
    List<PointOfInterest> getPointsOfInterestByType(ElementType type);

    public void clear();
}
