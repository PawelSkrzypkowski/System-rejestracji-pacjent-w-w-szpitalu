package pl.edu.wat.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VisitDTO {
    private Long userId;
    private Long doctorId;
    private Long visitId;
    private String visitDescription;
    private LocalDateTime visitDate;
    private String userName;
    private String doctorName;

    @Builder
    public VisitDTO(Long userId, Long doctorId, Long visitId, String visitDescription, LocalDateTime visitDate, String userName, String doctorName) {
        this.userId = userId;
        this.doctorId = doctorId;
        this.visitId = visitId;
        this.visitDescription = visitDescription;
        this.visitDate = visitDate;
        this.userName = userName;
        this.doctorName = doctorName;
    }
}
