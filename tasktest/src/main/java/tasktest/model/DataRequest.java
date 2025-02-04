package tasktest.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Component
public class DataRequest {

    private UUID valletId;
	private OperationType operationType;
    private int amount;
}
