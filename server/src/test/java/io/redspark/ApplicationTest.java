package io.redspark;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;

import io.redspark.security.UserAuthentication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes = {Application.class}, properties = "server.port=10001")
public abstract class ApplicationTest {

  private static List<Object> toPersist = new ArrayList<Object>();

  private final String server;
  private String authentication;

  @Autowired
  private JpaTransactionManager manager;

  @Autowired
  private JdbcTemplate template;

  public ApplicationTest() {
    this.server = "http://localhost:10001";
  }

  @Before
  public void setUp() {
    toPersist.clear();
    template.execute("TRUNCATE SCHEMA public AND COMMIT");
  }

  protected void signIn(UserAuthentication user) {
    ResponseEntity<Object> response = post("/login")
        .formParam("username", user.getLogin())
        .formParam("password", user.getPassword())
        .expectedStatus(HttpStatus.OK)
        .getResponse(Object.class);

    authentication = response.getHeaders().getFirst("Set-Cookie");
  }

  protected void add(Object... objects) {
    for (Object object : objects) {
      toPersist.add(object);
    }
  }

  protected void saveall(Object... objects) {
    this.add(objects);
    this.saveall();
  }

  protected void saveall() {
    EntityManager em = manager.getEntityManagerFactory().createEntityManager();
    em.getTransaction().begin();
    for (Object object : toPersist) {
      em.persist(object);
    }
    em.flush();
    em.clear();
    em.close();
    toPersist.clear();
    em.getTransaction().commit();
  }

  protected void signOut(UserAuthentication user) {
    this.authentication = null;
  }

  protected RequestBuilder get(String uri) {
    return new RequestBuilder(server, uri, HttpMethod.GET).header("Cookie", authentication);
  }

  protected RequestBuilder put(String uri) {
    return new RequestBuilder(server, uri, HttpMethod.PUT).header("Cookie", authentication);
  }

  protected RequestBuilder post(String uri) {
    return new RequestBuilder(server, uri, HttpMethod.POST).header("Cookie", authentication);
  }

  protected RequestBuilder delete(String uri) {
    return new RequestBuilder(server, uri, HttpMethod.DELETE).header("Cookie", authentication);
  }

  // With path variables
  protected RequestBuilder get(String uri, Object... path) {
    return get(String.format(uri, path));
  }

  protected RequestBuilder post(String uri, Object... path) {
    return post(String.format(uri, path));
  }

  protected RequestBuilder put(String uri, Object... path) {
    return put(String.format(uri, path));
  }

  protected RequestBuilder delete(String uri, Object... path) {
    return delete(String.format(uri, path));
  }

  protected TestRestTemplate template() {
    return new TestRestTemplate();
  }
}
