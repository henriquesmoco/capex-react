package com.hsmoco.capex.capexbackend.request;

import com.hsmoco.capex.capexbackend.request.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(value = "SELECT NEXT VALUE FOR seq_request_number", nativeQuery = true)
    Long getNextRequestNumber();

    Long countByParent(Request parent);
}
