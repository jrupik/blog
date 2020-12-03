package pl.training.blog.commons.jpa;

import javax.persistence.EntityManager;
import java.util.concurrent.CountDownLatch;

public interface Task extends Runnable {

    void setEntityManager(EntityManager entityManager);

    void setCountDownLatch(CountDownLatch countDownLatch);

}