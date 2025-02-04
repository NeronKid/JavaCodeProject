package tasktest.model;


import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "wallet_operations")
public class WalletOperation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "wallet_id")
	private UUID walletId;
	
	@Column(name = "value")
	private int value;
	
	@Column(name = "operationType")
	private OperationType operation;
	
	public WalletOperation(UUID walletId, int value, OperationType operation) {
		this.walletId = walletId;
		this.value = value;
		this.operation = operation;
	}
}
