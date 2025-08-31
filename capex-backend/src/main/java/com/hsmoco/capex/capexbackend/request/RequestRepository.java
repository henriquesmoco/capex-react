package com.hsmoco.capex.capexbackend.request;

import com.hsmoco.capex.capexbackend.request.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
