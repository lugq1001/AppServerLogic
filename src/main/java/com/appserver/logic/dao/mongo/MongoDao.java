package com.appserver.logic.dao.mongo;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

/**
 * Mogo CRUD封装
 * @author Luguangqing
 *
 * @param <T>
 */
public class MongoDao<T> extends BasicDAO<T, ObjectId> {

	public MongoDao(Class<T> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

	@Override
	public long count() {
		return super.count();
	}

	@Override
	public long count(Query<T> q) {
		return super.count(q);
	}

	@Override
	public long count(String key, Object value) {
		return super.count(key, value);
	}

	@Override
	public Query<T> createQuery() {
		return super.createQuery();
	}

	public Query<T> createQuery(String key, Object value) {
		return super.createQuery().field(key).equal(value);
	}

	@Override
	public UpdateOperations<T> createUpdateOperations() {
		return super.createUpdateOperations();
	}

	public WriteResult delete(T entity, boolean isSafe) {
		WriteConcern wc = new WriteConcern();
		if (isSafe) {
			wc = WriteConcern.SAFE;
		} else {
			wc = WriteConcern.NORMAL;
		}
		return super.delete(entity, wc);
	}

	@Override
	public WriteResult delete(T entity, WriteConcern wc) {
		return super.delete(entity, wc);
	}

	@Override
	public WriteResult delete(T entity) {
		return super.delete(entity);
	}

	@Override
	public WriteResult deleteByQuery(Query<T> q) {
		return super.deleteByQuery(q);
	}

	public WriteResult deleteByQuery(String key, Object value) {
		return super.deleteByQuery(createQuery().field(key).equal(value));
	}

	@Override
	public void ensureIndexes() {
		super.ensureIndexes();
	}

	@Override
	public boolean exists(Query<T> q) {
		return super.exists(q);
	}

	@Override
	public boolean exists(String key, Object value) {
		return super.exists(key, value);
	}

	@Override
	public QueryResults<T> find() {
		return super.find();
	}

	@Override
	public QueryResults<T> find(Query<T> q) {
		return super.find(q);
	}

	@Override
	public T findOne(Query<T> q) {
		return super.findOne(q);
	}

	@Override
	public T findOne(String key, Object value) {
		return super.findOne(key, value);
	}

	@Override
	public Key<T> save(T entity, WriteConcern wc) {
		return super.save(entity, wc);
	}

	public Key<T> save(T entity, boolean isSafe) {
		WriteConcern wc = new WriteConcern();
		if (isSafe) {
			wc = WriteConcern.SAFE;
		} else {
			wc = WriteConcern.NORMAL;
		}
		return super.save(entity, wc);
	}

	@Override
	public Key<T> save(T entity) {
		return super.save(entity);
	}

	@Override
	public UpdateResults update(Query<T> q, UpdateOperations<T> ops) {
		return super.update(q, ops);
	}

	public UpdateResults update(Query<T> q, UpdateOperations<T> ops, boolean createIfMissing) {
		return getDs().update(q, ops, createIfMissing);
	}

	@Override
	public UpdateResults updateFirst(Query<T> q, UpdateOperations<T> ops) {
		return super.updateFirst(q, ops);
	}

	public UpdateResults updateFirst(Query<T> q, UpdateOperations<T> ops, boolean createIfMissing) {
		return getDs().update(q, ops, createIfMissing);
	}

	public T findAndDelete(Query<T> q) {
		return getDs().findAndDelete(q);
	}

	public T findAndModify(Query<T> q, UpdateOperations<T> ops) {
		return getDs().findAndModify(q, ops);
	}

	public T findAndModify(Query<T> q, UpdateOperations<T> ops, boolean oldVersion) {
		return getDs().findAndModify(q, ops, oldVersion);
	}

	public T findAndModify(Query<T> q, UpdateOperations<T> ops, boolean oldVersion, boolean createIfMissing) {
		return getDs().findAndModify(q, ops, oldVersion, createIfMissing);
	}
}
