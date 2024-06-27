package tasktest.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tasktest.model.DataWallet;

@Repository
public interface WalletRepository extends JpaRepository<DataWallet, UUID> {
}
