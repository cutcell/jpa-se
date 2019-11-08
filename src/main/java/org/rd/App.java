package org.rd;

import org.h2.tools.RunScript;
import org.rd.service.EmployeeService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.DriverManager;

public class App {

    public static void main(String[] args) {
        if (!createTable()) {
            return;
        }

        var emf = Persistence.createEntityManagerFactory("jpa-se-employeeService");
        var em = emf.createEntityManager();
        var employeeService = new EmployeeService(em);

        System.out.println("Create employees");

        em.getTransaction().begin();
        employeeService.create(11, "test_employee1", BigDecimal.valueOf(1000));
        employeeService.create(12, "test_employee2", BigDecimal.valueOf(2222));
        employeeService.create(13, "test_employee3", BigDecimal.valueOf(3030));
        em.getTransaction().commit();

        employeeService.findAll().forEach(System.out::println);

        em.getTransaction().begin();
        employeeService.removeById(11);
        em.getTransaction().commit();

        System.out.println("After removal");

        employeeService.findAll().forEach(System.out::println);

        em.close();
        emf.close();

    }

    private static boolean createTable() {
        try (var connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
            var stmt = connection.createStatement()) {

            stmt.execute("create table if not exists employee\n" +
                    "(\n" +
                    "    id        bigint primary key,\n" +
                    "    full_name varchar(255),\n" +
                    "    salary    decimal(20, 2)\n" +
                    ")");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
