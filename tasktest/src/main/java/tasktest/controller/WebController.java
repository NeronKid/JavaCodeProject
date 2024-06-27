package tasktest.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	
	@Scheduled
	@PostMapping("api/v1/wallet")
	public BaseResponse<DataWallet> depositOrWithdraw(String valletId, String operationType, int ammount) {
		UUID uuid = UUID.fromString(valletId);
		DataWallet newWallet;
		if (ammount < 0) {
			return new BaseResponse<DataWallet>(406, "number of deposit/withdraw cannot be negative", null);
		}
		if(walletRepo.existsById(uuid)) {
			newWallet = walletRepo.getReferenceById(uuid);
		}
		else {
			newWallet = walletRepo.save(new DataWallet(uuid, operationType, 0));
		}
			
		if(operationType == OperationType.DEPOSIT.toString()) {
			newWallet.setBalance(newWallet.getBalance() + ammount);
		}
		else if (operationType == OperationType.WITHDRAW.toString()) {
			if (newWallet.getBalance() >= ammount)
				newWallet.setBalance(newWallet.getBalance() - ammount);
			else {
				return new BaseResponse<DataWallet>(406, "Not enough money on your account", null);
			}
		}
		else {
			
		}
		BaseResponse<DataWallet> newReq = new BaseResponse<>(200, "Your operation has been finished successfully",new DataWallet(uuid, operationType, ammount));
		walletRepo.save(newReq.getData());
		
		return newReq;
	}
	
	@Async
	@GetMapping("api/v1/wallets/{WALLET_UUID}")
	public BaseResponse<DataWallet> checkBalance(@PathVariable(value = "WALLET_UUID") String valletId){
		BaseResponse<DataWallet> newReq = new BaseResponse<>(500, "",new DataWallet(valletId));
		return newReq;
	}
}
