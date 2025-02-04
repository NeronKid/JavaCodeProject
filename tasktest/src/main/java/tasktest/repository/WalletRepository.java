package tasktest.repository;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tasktest.model.DataWallet;

@Repository
public interface WalletRepository extends JpaRepository<DataWallet, UUID> {
  
  @Query(value = "insert into wallet (wallet_id, balance) " +
            "  values (:#{#entity.walletId}, :#{#entity.balance}) " + 
            "  on conflict(wallet_id) do update set balance = :#{#entity.balance}" +
            "  returning *", nativeQuery = true)
  DataWallet saveOrUpdate(DataWallet entity);
}
