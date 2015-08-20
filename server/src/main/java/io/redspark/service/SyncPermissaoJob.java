package io.redspark.service;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import br.org.sesc.permissao.sync.SyncService;

@Service
@Profile(value={"QA", "PRODUCAO"})
public class SyncPermissaoJob {

	Integer timeoutInSeconds = 20;
    
    @Autowired
    private SyncService syncService;
    
    @PostConstruct
    public void syncronize(){
        final Timer t = new Timer();
       t.schedule(new TimerTask() 
       {
           @Override
           public void run() {
               syncService.syncronize();
               t.cancel();
           }
       }, timeoutInSeconds*1000);
    }

}