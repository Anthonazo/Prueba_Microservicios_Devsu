package com.devsu.hackerearth.backend.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsu.hackerearth.backend.account.model.ClientSnapshot;

public interface ClientSnapshotRepository extends JpaRepository<ClientSnapshot, Long> {

}
