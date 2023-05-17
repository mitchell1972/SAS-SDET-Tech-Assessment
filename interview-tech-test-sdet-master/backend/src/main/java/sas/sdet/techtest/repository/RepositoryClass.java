package sas.sdet.techtest.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import sas.sdet.techtest.domain.Order;
import sas.sdet.techtest.domain.Tournament;
import sas.sdet.techtest.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryClass {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public User loadUser(String name) {
		User user = em.find(User.class, name);
		return user;
	}

	@Transactional
	public Tournament loadItem(String name) {
		Tournament tournament = em.find(Tournament.class, name);
		return tournament;
	}

	@Transactional
	public Order order(String userName, String itemName) throws NotEnoughProException {
		User user = this.loadUser(userName);
		Tournament tournament = this.loadItem(itemName);
		if (user != null && tournament != null) {
			if (user.getDexterity() < tournament.getProfessionalism()) {
				throw new NotEnoughProException();
			}
			Order subscription = new Order();
			subscription.setUser(user);
			subscription.setItem(tournament);
			em.persist(subscription);
			return subscription;
		}
		return null;
	}

	public List<Order> multiOrder(String name, List<String> tournaments) throws NotEnoughProException {
		User user = this.loadUser(name);
		List<Order> orders = new ArrayList<Order>();
		if (em.contains(user)) {
			for (String tournamentName : tournaments) {
				Tournament tournament = em.find(Tournament.class, tournamentName);
				if (em.contains(tournament)) {
					orders.add(this.order(name, tournamentName));
				}
			}
		}
		return orders;
	}


	public List<Order> orderListByUser(String name) {
		TypedQuery<Order> query = em.createQuery("select order from Order order where order.user.name = :name",
				Order.class);
		query.setParameter("name", name);
		List<Order> ordersList = query.getResultList();
		return ordersList;
	}
}
