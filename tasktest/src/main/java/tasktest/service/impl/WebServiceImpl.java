package tasktest.service.impl;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tasktest.exception.WebServiceException;
import tasktest.model.DataRequest;
import tasktest.model.DataWallet;
import tasktest.model.OperationType;
import tasktest.repository.WalletOperationRepository;
import tasktest.repository.WalletRepository;
import tasktest.service.WebService;
import tasktest.model.WalletOperation;

@Service
public class WebServiceImpl implements WebService{
	
	@Autowired
	private WalletRepository walletRepo;
	@Autowired
	private WalletOperationRepository walletOperRepo;
	
	
	public DataWallet walletExistence (DataRequest data) throws WebServiceException {
		UUID uuid = data.getValletId();
		DataWallet newWallet;
		WalletOperation walletOperation;
		int ammount = data.getAmount();
		OperationType operation = data.getOperationType();
		Optional<DataWallet> dataUUID = walletRepo.findById(uuid);
		if(!dataUUID.isEmpty()) {
			newWallet = dataUUID.get();
		}
		else {
			newWallet = walletRepo.save(new DataWallet(uuid, 0));
		}
		if (ammount < 0) {
			throw new WebServiceException("Number can't be negative\n");
		}	
		switch(operation) {
		case DEPOSIT: 
			newWallet.setBalance(newWallet.getBalance() + ammount);
			walletOperation = new WalletOperation(uuid, ammount, operation);
			break;
		
		case WITHDRAW:
			if (newWallet.getBalance() >= ammount) {
                newWallet.setBalance(newWallet.getBalance() - ammount);
				walletOperation = new WalletOperation(uuid, ammount, operation);
            }
			else {throw new WebServiceException("Balance doesn't have enough money for withdraw\n");}
            break;	
		
		default: 
			throw new WebServiceException("OperationType isn't WITHDRAW or DEPOSIT");
		}
		
		walletRepo.save(newWallet);
		walletOperRepo.save(walletOperation);
		
		return newWallet;
	}
	public boolean walletMistakesCheck (DataRequest data) throws WebServiceException {
		if(data.getValletId() == null) {
			throw new WebServiceException("valletId cannot be null");
		}
		if(data.getOperationType() == null) {
			throw new WebServiceException("operationType cannot be null");
		}
		if (data.getAmount() <= 0) {
		    throw new WebServiceException("amount can't be negative\n");
		}
		OperationType operation = data.getOperationType();
		if (operation == null) {
			throw new WebServiceException("operation can't be " + data.getOperationType());
		}
		if(walletExistence(data) instanceof DataWallet) {
			return true;
		}
		return false;
	}
	
	public CompletableFuture<Integer> walletIsEmpty (String walletId) throws WebServiceException {
		UUID uuid = UUID.fromString(walletId);
		Optional<DataWallet> wallet = walletRepo.findById(uuid);
		if(wallet.isEmpty()) {
			throw new WebServiceException("wallet doesn't exist");
		}
		return CompletableFuture.completedFuture(wallet.get().getBalance());
	}
}

