package tasktest.repository;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tasktest.model.DataWallet;

@Repository
public interface WalletRepository extends JpaRepository<DataWallet, UUID> {
  
  @Query(value = "insert into wallet (wallet_id, balance, operation_type) " +
            "  values (:#{#entity.walletId}, :#{#entity.balance}, :#{#entity.operationType}) " + 
            "  on conflict(wallet_id) do update set foo = coalesce(entity.balance, wallet.balance) " +
            "    bar = coalesce(entity.operationType, wallet.operation_type) " +
            "  returning *", nativeQuery = true)
  DataWallet saveOrUpdate(DataWallet entity);
}
