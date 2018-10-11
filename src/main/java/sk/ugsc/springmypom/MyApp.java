package sk.ugsc.springmypom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Date;

@Configuration
@ComponentScan
public class MyApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyApp.class);
        TestClass testClass = ctx.getBean(TestClass.class);
        System.out.println(testClass.Test());
    }
}

@Component
class eventExample {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @EventListener
    public void contextRefresh(ContextRefreshedEvent event) {
        System.out.println("context refreshed");
        applicationEventPublisher.publishEvent(new MyEvent(new Date(), "My Event"));
    }

    @EventListener
    public void myEventListener(MyEvent myEvent) {
        System.out.println(myEvent);
    }
}


class MyEvent {
    public MyEvent(Date created, String message) {
        this.created = created;
        this.message = message;
    }

    private Date created;
    private String message;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MyEvent{" +
                "created=" + created +
                ", message='" + message + '\'' +
                '}';
    }
}

@Component
class TestClass {
    public String Test () {
        return "test";
    }
}
