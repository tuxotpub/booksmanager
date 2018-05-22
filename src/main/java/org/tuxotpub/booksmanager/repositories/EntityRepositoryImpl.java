package org.tuxotpub.booksmanager.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.NoRepositoryBean;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by tuxsamo.
 */
@Slf4j
@NoRepositoryBean
//@Repository as composition!
public abstract class EntityRepositoryImpl
        implements EntityRepository
{

    private final EntityManager em;

    public EntityRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * Find T and T#graphAttributes belonging to T as lazy mapping
     * <p>
     * (avoiding {@link org.hibernate.LazyInitializationException} by using graphs)
     *
     * @param  id   Id of the author to to find
     * @param clazz class of entity
     * @param graphAttributes graph attributes that is mapper tomany to load
     * @return      Type of T
     */
    @Override
    public  <T> Optional<T> findViaDynamicGraphById(Long id, Class<T> clazz, String ... graphAttributes){
        EntityGraph<T> graph = em.createEntityGraph(clazz);
        graph.addAttributeNodes(graphAttributes);
        Map<String, Object> hints = new HashMap();
        hints.put("javax.persistence.loadgraph", graph);
        T entity = em.find(clazz, id, hints);
        return Optional.ofNullable(entity);
    }
}
