package sas.sdet.techtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sas.sdet.techtest.repository.RepositoryClass;
import sas.sdet.techtest.repository.NotEnoughProException;
import sas.sdet.techtest.service.ServiceClass;
import sas.sdet.techtest.domain.Order;
import sas.sdet.techtest.domain.Tournament;
import sas.sdet.techtest.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(statements = { "delete from t_ordenes", "delete from t_items", "delete from t_users",
        "insert into t_users (user_name, user_prop) values ('Munson', 15)",
        "insert into t_users (user_name, user_prop) values ('McCracken', 100)",
        "insert into t_items (item_name, item_prop, item_type) values ('Murfreesboro Strike and Spare', 20, 'Torneo')",
        "insert into t_items (item_name, item_prop, item_type) values ('Bowlerama Lanes Iowa', 7, 'Torneo')",
        "insert into t_ordenes (ord_id, ord_user, ord_item) values (1,'Munson','Bowlerama Lanes Iowa')", })
public class KingPinTest {

    @Autowired
    private RepositoryClass repository;

    @Autowired
    private ServiceClass service;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testUserRetrieval() {
        // Objective: Verify that a user can be retrieved from the repository
        User user = repository.loadUser("Munson");
        assertNotNull(user);
        assertEquals("Munson", user.getName());
    }

    @Test
    public void testTournamentRetrieval() {
        // Objective: Verify that a tournament can be retrieved from the repository
        Tournament tournament = repository.loadItem("Murfreesboro Strike and Spare");
        assertNotNull(tournament);
        assertEquals("Murfreesboro Strike and Spare", tournament.getName());
    }

    @Test
    public void testOrderCreation() throws NotEnoughProException {
        // Objective: Verify that an order can be created with valid user and tournament
        Order order = repository.order("McCracken", "Murfreesboro Strike and Spare");
        assertNotNull(order);
        assertEquals("McCracken", order.getUser().getName());
        assertEquals("Murfreesboro Strike and Spare", order.getItem().getName());
    }

    @Test
    public void testOrderCreationFailure() {
        // Objective: Verify that an exception is thrown when creating an order with insufficient dexterity
        assertThrows(NotEnoughProException.class, () -> {
            repository.order("Munson", "Murfreesboro Strike and Spare");
        });
    }

    @Test
    public void testMultiOrderCreation() throws NotEnoughProException {
        // Objective: Verify that multiple orders can be created for a user
        List<String> tournaments = Arrays.asList("Murfreesboro Strike and Spare", "Bowlerama Lanes Iowa");
        List<Order> orders = repository.multiOrder("McCracken", tournaments);
        assertNotNull(orders);
        assertEquals(2, orders.size());
    }

    @Test
    public void testOrderRetrievalByUser() {
        // Objective: Verify that orders can be retrieved for a user
        List<Order> orders = service.orderListByUser("Munson");
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals("Munson", orders.get(0).getUser().getName());
        assertEquals("Bowlerama Lanes Iowa", orders.get(0).getItem().getName());
    }

    @Test
    @Transactional
    public void testTournamentCreation() {
        // Objective: Verify that a new tournament can be created and retrieved from the repository
        Tournament tournament = new Tournament();
        tournament.setName("New Tournament");
        tournament.setProfessionalism(10);
        tournament.setType("Torneo");

        entityManager.persist(tournament);
        entityManager.flush();

        Tournament retrievedTournament = repository.loadItem("New Tournament");
        assertNotNull(retrievedTournament);
        assertEquals("New Tournament", retrievedTournament.getName());
        assertEquals(10, retrievedTournament.getProfessionalism());
        assertEquals("Torneo", retrievedTournament.getType());
    }
}