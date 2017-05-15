package io.redspark.mock;

import java.util.List;

import br.org.sesc.notificacao.client.NotificationServiceClient;
import br.org.sesc.notificacao.client.exception.NotificationException;
import br.org.sesc.notificacao.client.model.Notification;
import br.org.sesc.notificacao.client.model.NotificationFilter;

public class NotificationServiceClientMock implements NotificationServiceClient {

  @Override
  public Notification getOne(Long notificationId) throws NotificationException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Notification> findAll(NotificationFilter filter) throws NotificationException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Notification create(Notification notification) throws NotificationException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Notification> create(List<Notification> notifications) throws NotificationException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Notification update(Notification notification) throws NotificationException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Notification> update(List<Notification> notifications) throws NotificationException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Notification updateAndRemove(Notification notification) throws NotificationException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Notification remove(Long notificationId) throws NotificationException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Notification> remove(List<Long> notifications) throws NotificationException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Notification> removeByUserIdAndSistemId(Long userId, Long sistemId) throws NotificationException {
    // TODO Auto-generated method stub
    return null;
  }

}
