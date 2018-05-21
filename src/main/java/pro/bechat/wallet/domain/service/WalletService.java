package pro.bechat.wallet.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.bechat.wallet.domain.dao.BasicMapper;
import pro.bechat.wallet.domain.dao.WalletMapper;
import pro.bechat.wallet.domain.model.model.Wallet;

/**
 * create WalletService by huc
 * 2018/4/19  上午3:08
 */
@Service
public class WalletService extends BasicService<Wallet> {

    @Autowired
    WalletMapper mapper;

    @Override
    protected BasicMapper basicMapper() {
        return mapper;
    }

    public boolean exist(String address){
        boolean exit = false;
        if (mapper.exist(address) == null){
            exit = true;
        }
        return exit;
    }

}
