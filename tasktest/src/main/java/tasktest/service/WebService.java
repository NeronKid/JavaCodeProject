package tasktest.service;

import tasktest.exception.ErrorHandler;
import tasktest.model.DataRequest;
import tasktest.model.DataWallet;

public interface WebService {
	
	public DataWallet walletExistence (DataRequest data) throws ErrorHandler;
	
	public boolean walletMistakesCheck (DataRequest data) throws ErrorHandler;
	
	public int walletIsEmpty (String walletId) throws ErrorHandler;
}
