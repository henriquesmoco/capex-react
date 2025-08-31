package com.hsmoco.capex.capexbackend.request;

import com.hsmoco.capex.capexbackend.request.model.BusinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Long> {
}
