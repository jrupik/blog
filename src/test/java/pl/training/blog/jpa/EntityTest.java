package pl.training.blog.jpa;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier
// https://vladmihalcea.com/hibernate-facts-equals-and-hashcode
public abstract class EntityTest<T extends Identifiable<Long>> extends BaseTest {

    protected final Set<T> entities = new HashSet<>();
    protected final EntityManager entityManager = entityManagerFactory.createEntityManager();
    protected final EntityTransaction transaction = entityManager.getTransaction();

    protected T entity;

    @BeforeEach
    void setup() {
        initializeEntity();
        entities.add(entity);
        transaction.begin();
    }

    protected abstract void initializeEntity();

    @Test
    void shouldReturnEntityFromSet() {
        Assertions.assertTrue(entities.contains(entity));
    }

    @Test
    void shouldReturnEntityFromSetAfterPersist() {
        entityManager.persist(entity);
        Assertions.assertTrue(entities.contains(entity));
    }

    @Test
    void shouldReturnEntityFromSetAfterMerge() {
        entityManager.persist(entity);
        var mergedPayment = entityManager.merge(entity);
        entityManager.flush();
        Assertions.assertTrue(entities.contains(mergedPayment));
    }

    @Test
    void shouldReturnEntityFromSetAfterRemove() {
        entityManager.persist(entity);
        var deletedEntity = entityManager.getReference(entity.getClass(), entity.getId());
        entityManager.remove(deletedEntity);
        Assertions.assertTrue(entities.contains(deletedEntity));
    }

    @Test
    void entityShouldEqualProxy() {
        entityManager.persist(entity);
        var entityProxy = entityManager.getReference(entity.getClass(), entity.getId());
        Assertions.assertEquals(entity, entityProxy);
    }

    @Test
    void proxyShouldEqualEntity() {
        entityManager.persist(entity);
        var entityProxy = entityManager.getReference(entity.getClass(), entity.getId());
        Assertions.assertEquals(entityProxy, entity);
    }

    @Test
    void shouldReturnEntityFromSetAfterReattach() {
        entityManager.persist(entity);
        entityManager.unwrap(Session.class).update(entity);
        Assertions.assertTrue(entities.contains(entity));
    }

    @Test
    void shouldReturnEntityLoadedWithFind() {
        entityManager.persist(entity);
        var foundEntity = entityManager.find(entity.getClass(), entity.getId());
        Assertions.assertTrue(entities.contains(foundEntity));
    }

    @Test
    void shouldReturnEntityLoadedWithGetReference() {
        entityManager.persist(entity);
        var foundEntity = entityManager.getReference(entity.getClass(), entity.getId());
        Assertions.assertTrue(entities.contains(foundEntity));
    }

    @AfterEach
    @Override
    void onClose() {
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

}