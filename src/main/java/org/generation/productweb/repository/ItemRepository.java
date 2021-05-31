package org.generation.productweb.repository;

import org.generation.productweb.repository.Entity.Item;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called itemRepository
// CRUD refers Create, Read, Update, Delete
public interface ItemRepository extends CrudRepository<Item, Integer>
{
}

