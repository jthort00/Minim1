package edu.upc.dsa;

import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PointOfInterest;
import edu.upc.dsa.models.Usuari;

import java.util.*;
import java.time.LocalDate;

import org.apache.log4j.Logger;

public class CampusManagerImpl implements CampusManager {
    private static CampusManager instance;
    final static Logger logger = Logger.getLogger(CampusManagerImpl.class);

    private Map<String, Usuari> users;
    private Map<String, PointOfInterest> pointsOfInterest;
    private Map<String, List<PointOfInterest>> userVisits;



    public CampusManagerImpl() {
        this.users = new HashMap<>();
        this.pointsOfInterest = new HashMap<>();
        this.userVisits = new HashMap<>();

    }

    public static CampusManager getInstance() {
        if (instance==null) instance = new CampusManagerImpl();
        return instance;
    }

    @Override
    public void addUser(String id, String name, String surname, String email, LocalDate birthDate) {
        logger.info("new Usuari " + name);
        users.put(id, new Usuari(id, name, surname, email, birthDate));
        logger.info("new Usuari added");
    }

    @Override
    public void addUser(Usuari t) {
        logger.info("new Usuari " + t);
        this.users.put(t.getId(),t);
        logger.info("new Usuari added");
    }

    @Override
    public List<Usuari> listUsersAlphabetically() {
        List<Usuari> userList = new ArrayList<>(users.values());
        userList.sort(Comparator.comparing(Usuari::getSurname).thenComparing(Usuari::getName));
        return userList;
    }

    @Override
    public Usuari getUsuari(String id) {
        Usuari user = this.users.get(id);
        if (user == null) {
            logger.error("User not found");
        }
        return user;
    }

    @Override
    public void addPointOfInterest(int x, int y, ElementType type) {
        String key = x + "," + y;
        pointsOfInterest.put(key, new PointOfInterest(x, y, type));
    }

    public void addPointOfInterest(PointOfInterest p) {
        logger.info("new Point of Interest " + p);
        String key = p.getX() + "," + p.getY();
        this.pointsOfInterest.put(key,p);
        logger.info("new Usuari added");
    }

    public PointOfInterest getPointOfInterest(int x, int y) {
        String key = x + "," + y;
        PointOfInterest point = pointsOfInterest.get(key);
        if (point == null) {
            logger.error("Point of Interest not found");
        }
        return point;
    }

    @Override
    public void registerUserVisit(String userId, int x, int y) {
        String key = x + "," + y;
        Usuari user = users.get(userId);
        PointOfInterest point = pointsOfInterest.get(key);

        if (user == null || point == null) {
            throw new IllegalArgumentException("User or Point of Interest not found");
        }

        // Register the visit
        point.getUsersVisited().add(userId);
        userVisits.computeIfAbsent(userId, k -> new ArrayList<>()).add(point);
    }

    @Override
    public List<PointOfInterest> getUserVisitedPoints(String userId) {
        return userVisits.getOrDefault(userId, new ArrayList<>());
    }

    @Override
    public List<Usuari> getUsersByPointOfInterest(int x, int y) {
        String key = x + "," + y;
        PointOfInterest point = pointsOfInterest.get(key);

        if (point == null) {
            throw new IllegalArgumentException("Point of Interest not found");
        }

        List<Usuari> userList = new ArrayList<>();
        for (String userId : point.getUsersVisited()) {
            userList.add(users.get(userId));
        }
        return userList;
    }

    @Override
    public List<PointOfInterest> getPointsOfInterestByType(ElementType type) {
        List<PointOfInterest> result = new ArrayList<>();
        for (PointOfInterest point : pointsOfInterest.values()) {
            if (point.getType() == type) {
                result.add(point);
            }
        }
        return result;
    }

    public void clear() {
        users.clear();
        pointsOfInterest.clear();
        userVisits.clear();
    }
}