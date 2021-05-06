package com.easybuild.site.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T extends Object> {
	/**
	 * Save an entity.
	 * @param t
	 */
	T create(T t);
	/**
	 * Get an entity specified by its primary key.
	 * @param id
	 * @return
	 */
	T get(Serializable id);
	/**
	 * Load an entity specified by its primary key.
	 * @param id
	 * @return
	 */
	T load(Serializable id);
	/**
	 * Get List of all data from table specified by entity.
	 * @return
	 */
	List<T> getAll();
	/**
	 * Get List of all data from table specified by entity.
	 * @return
	 */
	List<T> getAll(T t);
	/**
	 * Update an entity with updated data.
	 * @param t
	 */
	void update(T t);
	/**
	 * Delete entity .
	 * @param t
	 */
	void delete(T t);
	/**
	 * Delete an entity specified by its primary key id.
	 * @param id
	 */
	void deleteById(Serializable id);
	/**
	 * Delete all records from table.
	 */
	void deleteAll();
	/**
	 * Count records exist in table.
	 * @return
	 */
	long count();
	/**
	 * Check entity specified by primary key exist.
	 * @param id
	 * @return
	 */
	boolean exists(Serializable id);
	
}
