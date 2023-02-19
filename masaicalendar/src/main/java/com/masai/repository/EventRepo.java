package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.model.Event;

public interface EventRepo extends JpaRepository<Event, Integer>{

}
