package org.taitai.tutor_backend.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.taitai.tutor_backend.model.Classes;
@Getter
@Setter
@Builder
public class TutorAssignmentResponse {
    private Classes classes;
}
