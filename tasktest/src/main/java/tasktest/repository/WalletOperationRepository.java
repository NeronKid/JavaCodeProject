package tasktest.repository;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tasktest.model.DataWallet;
import tasktest.model.WalletOperation;

@Repository
public interface WalletOperationRepository extends JpaRepository<WalletOperation, UUID> {
	
	@Query(value  = "INSERT INTO wallet_operations (wallet_id, value, operation_type) VALUES (:#{#entity.walletId}, :#{#entity.value}, :#{#entity.operation})" + "returning *", nativeQuery = true)
	WalletOperation save(WalletOperation entity);
}
