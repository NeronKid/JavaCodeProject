package tasktest.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import tasktest.exception.ErrorHandler;
import tasktest.model.DataRequest;
import tasktest.model.DataWallet;
import tasktest.model.OperationType;
import tasktest.repository.WalletRepository;
import tasktest.service.WebService;

@Service
public class WebServiceImpl implements WebService{
	
	WalletRepository walletRepo;
	
	public DataWallet walletExistence (DataRequest data) throws ErrorHandler {
		UUID uuid = data.getValletId();
		DataWallet newWallet;
		int ammount = data.getAmount();
		OperationType operation = data.getOperationType();
		Optional<DataWallet> dataUUID = walletRepo.findById(uuid);
		if(!dataUUID.isEmpty()) {
			newWallet = dataUUID.get();
		}
		else {
			newWallet = walletRepo.save(new DataWallet(uuid, operation, 0));
		}
		if (ammount < 0) {
			throw new ErrorHandler(406, "Number can't be negative\n");
		}	
		switch(operation) {
		case DEPOSIT: 
			newWallet.setBalance(newWallet.getBalance() + ammount);
			newWallet.setOperationType(operation);
			break;
		
		case WITHDRAW:
			if (newWallet.getBalance() >= ammount) {
                newWallet.setBalance(newWallet.getBalance() - ammount);
                newWallet.setOperationType(operation);
            }
			else {throw new ErrorHandler(406, "Balance doesn't have enough money for withdraw\n");}
            break;	
		
		default: 
			throw new ErrorHandler(406, "OperationType isn't WITHDRAW or DEPOSIT");
		}
		
		walletRepo.save(newWallet);
		
		return newWallet;
	}
	public boolean walletMistakesCheck (DataRequest data) throws ErrorHandler {
		if(data.getValletId() == null) {
			throw new ErrorHandler(406, "valletId cannot be null");
		}
		if(data.getOperationType() == null) {
			throw new ErrorHandler(406, "operationType cannot be null");
		}
		if (data.getAmount() <= 0) {
		    throw new ErrorHandler(406, "amount can't be negative\n");
		}
		OperationType operation = data.getOperationType();
		if (operation == null) {
			throw new ErrorHandler(406, "operation can't be " + data.getOperationType());
		}
		if(walletExistence(data) instanceof DataWallet) {
			return true;
		}
		return false;
	}
	
	public int walletIsEmpty (String walletId) throws ErrorHandler {
		UUID uuid = UUID.fromString(walletId);
		Optional<DataWallet> wallet = walletRepo.findById(uuid);
		if(wallet.isEmpty()) {
			throw new ErrorHandler(406, "wallet doesn't exist");
		}
		return wallet.get().getBalance();
	}
}

