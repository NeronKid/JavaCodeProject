package tasktest.model;

import java.util.UUID;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import tasktest.repository.WalletRepository;


@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "wallet")
public class DataWallet {
	
	@Id
    private UUID walletId;
	
	@Enumerated(EnumType.STRING)
	private OperationType operationType;
	
	@Column(name="balance")
    private int balance;


	public int checkBalance(WalletRepository walletRepo, UUID uuid) {
		DataWallet wallet = walletRepo.getReferenceById(uuid);
		return wallet.getBalance();
	}
}
