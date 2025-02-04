package tasktest.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tasktest.exception.ErrorHandler;
import tasktest.exception.WebServiceException;
import tasktest.model.DataRequest;
import tasktest.model.DataWallet;

import tasktest.service.WebService;

@RestController
@RequiredArgsConstructor
public class WebController {

	@Autowired
	private WebService service;
	
	@Async
	@PostMapping(value = "api/v1/wallet")
	public CompletableFuture<DataWallet> depositOrWithdraw(@RequestBody DataRequest data) throws WebServiceException {
		return CompletableFuture.completedFuture(service.walletExistence(data));
	}
	
	@Async
	@GetMapping("api/v1/wallets/{WALLET_UUID}")
	public CompletableFuture<Integer> checkBalance(@PathVariable(value = "WALLET_UUID") String valletId) throws WebServiceException {
		return service.walletIsEmpty(valletId);
	}
}
