package tasktest.service;

import tasktest.exception.ErrorHandler;
import tasktest.exception.WebServiceException;
import tasktest.model.DataRequest;
import tasktest.model.DataWallet;

public interface WebService {
	
	public DataWallet walletExistence (DataRequest data) throws WebServiceException;
	
	public boolean walletMistakesCheck (DataRequest data) throws WebServiceException;
	
	public int walletIsEmpty (String walletId) throws WebServiceException;
}
