package org.rd.service;

import org.rd.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

public class EmployeeService {

    private EntityManager em;

    public EmployeeService(EntityManager em) {
        this.em = em;
    }

    public List<Employee> findAll() {
        TypedQuery<Employee> query = em.createQuery("select e from Employee e", Employee.class);
        return query.getResultList();
    }

    public Employee findById(long id) {
        return em.find(Employee.class, id);
    }

    public Employee create(long id, String fullName, BigDecimal salary) {
        Employee e = new Employee(id, fullName, salary);
        em.persist(e);
        return e;
    }

    public void removeById(long id) {
        Employee e = findById(id);
        if (e != null) {
            em.remove(e);
        }
    }

}
