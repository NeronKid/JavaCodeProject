package tasktest.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tasktest.model.BaseResponse;
import tasktest.model.DataWallet;
import tasktest.model.OperationType;
import tasktest.repository.WalletRepository;

@RestController
@RequiredArgsConstructor
public class WebController {
	
	private final WalletRepository walletRepo;
	
	@PostMapping
	public void getJson(@RequestBody DataWallet wallet) throws ErrorHandler {
		if (wallet != null) {
			DataWallet json = wallet;
		}
		else throw  new ErrorHandler(406, "Number can't be negative");
	}
	
	@Scheduled
	@PostMapping("api/v1/wallet")
	public DataWallet depositOrWithdraw(String valletId, String operationType, int ammount) throws ErrorHandler {
		if (valletId == null || operationType == null || ammount == 0) {
			throw new ErrorHandler(406, "values cannot be null or 0\n");
		}
		UUID uuid = UUID.fromString(valletId);
		DataWallet newWallet;
		if(walletRepo.existsById(uuid)) {
			newWallet = walletRepo.getReferenceById(uuid);
		}
		else {
			
			newWallet = walletRepo.save(new DataWallet(uuid, OperationType.of(operationType), 0));
		}
		if (ammount < 0) {
			throw new ErrorHandler(406, "Number can't be negative\n");
		}	
		if(OperationType.of(operationType) == OperationType.DEPOSIT) {
			newWallet.setBalance(newWallet.getBalance() + ammount);
		}
		else if (OperationType.of(operationType) == OperationType.WITHDRAW) {
			if (newWallet.getBalance() >= ammount)
				newWallet.setBalance(newWallet.getBalance() - ammount);
			else {
				throw new ErrorHandler(406, "Balance doesn't have enough money for withdraw\n");
			}
		}
		else {
			throw new ErrorHandler(406, "Operation type isn't right\n");		
		}
		
		walletRepo.save(newWallet);
		
		return newWallet;
	}
	
	@Async
	@GetMapping("api/v1/wallets/{WALLET_UUID}")
	public int checkBalance(@PathVariable(value = "WALLET_UUID") String valletId) throws ErrorHandler{
		UUID uuid = UUID.fromString(valletId);
		if(walletRepo.getReferenceById(uuid) == null) {
			throw new ErrorHandler(406, "Operation type isn't right");
		}
		DataWallet wallet = walletRepo.getReferenceById(uuid);
		return wallet.checkBalance(walletRepo, uuid);
	}
}
