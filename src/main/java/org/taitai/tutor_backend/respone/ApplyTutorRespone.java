package org.taitai.tutor_backend.respone;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.taitai.tutor_backend.type.ApplyStatus;

@Getter
@Setter
@Builder
public class ApplyTutorRespone {
    private Long tutorId;
    private Long classId;
    private ApplyStatus status;
}
