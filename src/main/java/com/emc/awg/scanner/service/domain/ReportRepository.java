package com.emc.awg.scanner.service.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="/report")
public interface ReportRepository extends JpaRepository<Report,Long>{

}
