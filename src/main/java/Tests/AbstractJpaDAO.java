//package Tests;
//
//import java.io.Serializable;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//public abstract class AbstractJpaDAO<T extends Serializable> {
//
//	private Class<T> clazz;
//
//	@PersistenceContext
//	private EntityManager entityManager;
//
//	public void create(final T entity) {
//		entityManager.persist(entity);
//	}
//
//	public void delete(final T entity) {
//		entityManager.remove(entity);
//	}
//
//	public void deleteById(final long entityId) {
//		final T entity = findOne(entityId);
//		delete(entity);
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<T> findAll() {
//		return entityManager.createQuery("from " + clazz.getName())
//				.getResultList();
//	}
//
//	public T findOne(final long id) {
//		return entityManager.find(clazz, id);
//	}
//
//	public final void setClazz(final Class<T> clazzToSet) {
//		this.clazz = clazzToSet;
//	}
//
//	public T update(final T entity) {
//		return entityManager.merge(entity);
//	}
//
//}