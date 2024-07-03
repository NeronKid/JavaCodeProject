package tasktest.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import tasktest.model.BaseResponse;
import tasktest.model.DataRequest;
import tasktest.model.DataWallet;
import tasktest.model.OperationType;
import tasktest.repository.WalletRepository;

@RestController
@RequiredArgsConstructor
public class WebController {
	
	private final WalletRepository walletRepo;
	
	@PostMapping(value = "api/v1/wallet")
	public DataWallet depositOrWithdraw(@RequestBody DataRequest data) throws ErrorHandler {
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
		UUID uuid = data.getValletId();
		DataWallet newWallet;
		int amount = data.getAmount();
		Optional<DataWallet> dataUUID = walletRepo.findById(uuid);
		
		if(!dataUUID.isEmpty()) {
			newWallet = dataUUID.get();
		}
		else {
			
			newWallet = walletRepo.save(new DataWallet(uuid, operation, 0));
		}
		if (amount < 0) {
			throw new ErrorHandler(406, "Number can't be negative\n");
		}	
		switch(operation) {
		case DEPOSIT: 
			newWallet.setBalance(newWallet.getBalance() + amount);
			break;
		
		case WITHDRAW:
			if (newWallet.getBalance() >= amount) {
                newWallet.setBalance(newWallet.getBalance() - amount);
            }
			else {throw new ErrorHandler(406, "Balance doesn't have enough money for withdraw\n");}
            break;	
		
		default: 
			throw new ErrorHandler(406, "OperationType isn't WITHDRAW or DEPOSIT");
		}
		
		walletRepo.save(newWallet);
		
		return newWallet;
	}
	
	@GetMapping("api/v1/wallets/{WALLET_UUID}")
	public int checkBalance(@PathVariable(value = "WALLET_UUID") String valletId) throws ErrorHandler{
		UUID uuid = UUID.fromString(valletId);
		Optional<DataWallet> wallet = walletRepo.findById(uuid);
		if(wallet.isEmpty()) {
			throw new ErrorHandler(406, "Operation type isn't right");
		}
		return wallet.get().getBalance();
	}
}
