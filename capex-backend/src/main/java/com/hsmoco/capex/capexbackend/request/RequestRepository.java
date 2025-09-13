package com.hsmoco.capex.capexbackend.request;

import com.hsmoco.capex.capexbackend.request.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends PagingAndSortingRepository<Request, Long>, JpaRepository<Request, Long> {

    @Query(value = "SELECT NEXT VALUE FOR seq_request_number", nativeQuery = true)
    Long getNextRequestNumber();

    Long countByParent(Request parent);

}
