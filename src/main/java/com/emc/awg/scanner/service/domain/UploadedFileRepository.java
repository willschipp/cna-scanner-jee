package com.emc.awg.scanner.service.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="/uploads")
public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {

}
