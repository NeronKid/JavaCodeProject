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
	@Column(name="wallet_id")
    private UUID walletId;
	
	@Column(name="balance")
    private int balance;

}
