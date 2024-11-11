package edu.upc.dsa;

import edu.upc.dsa.models.Usuari;
import edu.upc.dsa.models.PointOfInterest;
import edu.upc.dsa.models.ElementType;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.time.LocalDate;
public class CampusManagerTest {
    private CampusManager manager;

    @Before
    public void setUp() {
        manager = CampusManagerImpl.getInstance();
        manager.addUser("u1", "Anna", "Martínez", "anna@mail.com", LocalDate.of(1999, 5, 10));
        manager.addUser("u2", "Bernat", "Lopez", "bernat@mail.com", LocalDate.of(1998, 6, 20));
        manager.addPointOfInterest(1, 2, ElementType.DOOR);
    }

    @After
    public void tearDown() {
        // És un Singleton
        this.manager.clear();
    }

    @Test
    public void testAddAndListUsers() {
        List<Usuari> users = manager.listUsersAlphabetically();
        Assert.assertEquals("Lopez", users.get(0).getSurname());
        Assert.assertEquals("Martínez", users.get(1).getSurname());
    }

    @Test
    public void testRegisterVisit() {
        manager.registerUserVisit("u1", 1, 2);
        List<PointOfInterest> visitedPoints = manager.getUserVisitedPoints("u1");
        Assert.assertEquals(1, visitedPoints.size());
        Assert.assertEquals(1, visitedPoints.get(0).getX());
    }

    @Test
    public void testListUsersAlphabetically() {
        manager.clear();
        manager.addUser("u1", "Anna", "Martinez", "anna@mail.com", LocalDate.of(1999, 5, 10));
        manager.addUser("u2", "Bernat", "Garcia", "bernat@mail.com", LocalDate.of(1998, 6, 20));
        manager.addUser("u3", "Anna", "Alonso", "anna@mail.com", LocalDate.of(1999, 5, 10));

        List<Usuari> users = this.manager.listUsersAlphabetically();

        Assert.assertEquals(3, users.size());
        Assert.assertEquals("Alonso", users.get(0).getSurname());
        Assert.assertEquals("Garcia", users.get(1).getSurname());
        Assert.assertEquals("Martinez", users.get(2).getSurname());
    }

    @Test
    public void testUsersWhoPassedThroughPoint() {
        Usuari user1 = new Usuari("1", "Joan", "Garcia", "joan@example.com", LocalDate.of(1999, 5, 10));
        Usuari user2 = new Usuari("2", "Anna", "Martinez", "anna@example.com", LocalDate.of(1998, 6, 20));
        manager.addUser(user1);
        manager.addUser(user2);

        PointOfInterest poi = new PointOfInterest(2, 3, ElementType.BRIDGE);
        manager.addPointOfInterest(poi);

        manager.registerUserVisit(user1.getId(), 2, 3);
        manager.registerUserVisit(user2.getId(), 2, 3);

        List<Usuari> users = manager.getUsersByPointOfInterest(2, 3);

        Assert.assertEquals(2, users.size());
        Assert.assertTrue(users.contains(user1));
        Assert.assertTrue(users.contains(user2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUsersWhoPassedThroughNonexistentPoint() {
        manager.getUsersByPointOfInterest(10, 10); // Coordenades sense punt d'interès
    }

    @Test
    public void testListPointsOfType() {
        manager.clear();
        manager.addPointOfInterest(new PointOfInterest(1, 1, ElementType.TREE));
        manager.addPointOfInterest(new PointOfInterest(2, 3, ElementType.DOOR));
        manager.addPointOfInterest(new PointOfInterest(5, 7, ElementType.TREE));

        List<PointOfInterest> trees = this.manager.getPointsOfInterestByType(ElementType.TREE);

        Assert.assertEquals(2, trees.size());
    }


}
