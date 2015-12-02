package io.redspark;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.Arrays;

import org.springframework.boot.test.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestBuilder {

  private ObjectMapper mapper = new ObjectMapper();
  private TestRestTemplate rest = new TestRestTemplate();
  private String URI;
  private MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
  private MultiValueMap<String, Object> variables = new LinkedMultiValueMap<String, Object>();
  private MultiValueMap<String, String> queryString = new LinkedMultiValueMap<String, String>();
  private HttpMethod method;
  private String server;
  private HttpStatus status;
  private Object json;

  public RequestBuilder(String server, String URI, HttpMethod method) {
    super();
    this.server = server;
    this.URI = URI;
    this.method = method;
  }

  public RequestBuilder formParam(String key, Object value) {
    if (this.json != null) {
      throw new RuntimeException(
          "Can't use formParam and json body on the same request");
    }
    this.variables.add(key, value);
    return this;
  }

  public RequestBuilder queryParam(String key, Object value) {
    this.queryString.add(key, value.toString());
    return this;
  }

  public RequestBuilder header(String key, String value) {
    if (value != null) {
      this.headers.add(key, value);
    }
    return this;
  }

  public RequestBuilder json(Object json) {
    if (this.variables.size() > 0) {
      throw new RuntimeException(
          "Can't use formParam and json body on the same request");
    }
    this.json = json;
    return this;
  }

  public RequestBuilder expectedStatus(HttpStatus status) {
    this.status = status;
    return this;
  }

  @SuppressWarnings("rawtypes")
  public <T> ResponseEntity<T> getResponse(Class<T> responseType) {
    HttpEntity entity;
    if (HttpMethod.GET.equals(method)) {
      entity = new HttpEntity<Void>(headers);
    } else {
      if (this.json != null) {
        try {
          this.header("Content-Type",
              "application/json;charset=UTF-8");
          entity = new HttpEntity<String>(
              mapper.writeValueAsString(this.json), headers);
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      } else {
        entity = new HttpEntity<MultiValueMap<String, Object>>(
            variables, headers);
      }
    }

    URI link = UriComponentsBuilder.fromHttpUrl(server).path(URI)
        .queryParams(queryString).build().toUri();

    ResponseEntity<T> response = rest.exchange(link, method, entity,
        responseType);

    if (status != null) {
      assertThat(response.getStatusCode(), equalTo(status));
    }
    return response;
  }

  @SuppressWarnings("unchecked")
  public <T> Page<T> getPage(Class<T> clazz) {
    try {
      Class<T> class1 = (Class<T>) Array.newInstance(clazz, 0).getClass();
      JsonNode node = getJson();
      JsonNode content = node.get("content");
      T[] readValue = (T[]) mapper.readValue(content.toString(), class1);
      return new PageImpl<T>(Arrays.asList(readValue), new PageRequest(
          node.get("number").asInt(), node.get("size").asInt()), node
              .get("totalElements").asInt());
    } catch (Exception e) {
      throw new RuntimeException("Can't convert to PAGE");
    }
  }

  public JsonNode getJson() throws JsonProcessingException, IOException {
    return mapper.readTree(getResponse(String.class).getBody());
  }

  public ResponseEntity<Object> getResponse() {
    return this.getResponse(Object.class);
  }

  public void errorMessage(String errorMessage) {
    try {
      JsonNode json = this.getJson();
      assertThat(json.get("message").asText(), equalTo(errorMessage));
    } catch (IOException e) {
      throw new RuntimeException("Can't convert response to Json");
    }
  }
}
