package sas.sdet.techtest.service;

import java.util.List;

import sas.sdet.techtest.domain.Order;
import sas.sdet.techtest.repository.RepositoryClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceClass {

	@Autowired
	RepositoryClass repositoryClass;

	public List<Order> orderListByUser(String name) {
		return repositoryClass.orderListByUser(name);
	}
}