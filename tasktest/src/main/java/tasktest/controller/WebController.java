package tasktest.controller;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import tasktest.exception.ErrorHandler;
import tasktest.model.BaseResponse;
import tasktest.model.DataRequest;
import tasktest.model.DataWallet;
import tasktest.model.OperationType;
import tasktest.repository.WalletRepository;
import tasktest.service.WebService;

@RestController
@RequiredArgsConstructor
public class WebController {
	
	private final WalletRepository walletRepo;
	private WebService service;
	
	
	//@Async
	@PostMapping(value = "api/v1/wallet")
	public DataWallet depositOrWithdraw(@RequestBody DataRequest data) throws ErrorHandler {
			DataWallet newWallet;
			if(service.walletMistakesCheck(data)) {
					newWallet = service.walletExistence(data);
					walletRepo.save(newWallet);	
				
			}
			else {
				throw new ErrorHandler(406, "Mistake out of bounds");
			}

			return newWallet;
	}
	
	//@Async
	@GetMapping("api/v1/wallets/{WALLET_UUID}")
	public int checkBalance(@PathVariable(value = "WALLET_UUID") String valletId) throws ErrorHandler{
		UUID uuid = UUID.fromString(valletId);
		Optional<DataWallet> wallet = walletRepo.findById(uuid);
		if(wallet.isEmpty()) {
			throw new ErrorHandler(406, "wallet doesn't exist");
		}
		return wallet.get().getBalance();
	}
}
