package pro.bechat.wallet.domain.service;

import org.springframework.stereotype.Service;
import pro.bechat.wallet.publics.MnemonitUtitls;

import java.io.IOException;
import java.util.List;

/**
 * @Author ch
 * @Date Create int 15:
 */
@Service
public class MnemonitService {

    /**
     * 获得助记词
     *
     * @return
     */
    public String getMnemonit() throws IOException {
        return MnemonitUtitls.generateMnemonic();
    }


}
