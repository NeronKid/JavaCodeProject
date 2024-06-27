package tasktest.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "wallet")
public class DataWallet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
    private UUID walletId;
	
	@Enumerated(EnumType.STRING)
	private OperationType operationType;
	
	@Column(name="balance")
    private int balance;
	
	public DataWallet(UUID id, String operType, int balance) {
		
	}

}
