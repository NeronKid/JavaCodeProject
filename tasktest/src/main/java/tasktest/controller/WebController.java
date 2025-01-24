package tasktest.controller;

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

	private WebService service;
	
	@Async
	@PostMapping(value = "api/v1/wallet")
	public DataWallet depositOrWithdraw(@RequestBody DataRequest data) throws WebServiceException {
			return service.walletExistence(data);
	}
	
	@Async
	@GetMapping("api/v1/wallets/{WALLET_UUID}")
	public int checkBalance(@PathVariable(value = "WALLET_UUID") String valletId) throws WebServiceException {
		return service.walletIsEmpty(valletId);
	}
}
