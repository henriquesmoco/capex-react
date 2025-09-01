package com.hsmoco.capex.capexbackend.request.model;

import com.hsmoco.capex.capexbackend.request.RequestRepository;
import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class RequestNumberEntityListener {

    private final RequestRepository requestRepository;

    public RequestNumberEntityListener(@Lazy RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    /**
     * If the <code>ID</code> is null, ignores the <code>request number</code> and generates a new number.
     * For parent requests the number is <code>Type + Sequence</code>, if not it's
     * <code>Type + Sequence + '-S' + Parent Child Count</code>.
     * <br><br>
     * Examples: CAR1234, CAR1234-S5
     * @param request The request to receive the Request Number
     */
    @PrePersist
    @Transactional
    public void generateRequestNumber(Request request) {
        if (request.getId() != null) {
            return;
        }
        if (request.getParent() == null) {
            Long nextRequestNumber = requestRepository.getNextRequestNumber();
            request.setRequestNumber(String.format("%s%04d", request.getType().name(), nextRequestNumber));
        } else {
            Long childCount = requestRepository.countByParent(request.getParent());
            request.setRequestNumber(
                    StringUtils.join(request.getParent().getRequestNumber(), "-S", (childCount + 1)));
        }
    }
}
