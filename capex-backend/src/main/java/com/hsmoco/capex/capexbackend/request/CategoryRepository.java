package com.hsmoco.capex.capexbackend.request;

import com.hsmoco.capex.capexbackend.request.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
