package pro.bechat.wallet.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.bechat.wallet.domain.dao.BasicMapper;
import pro.bechat.wallet.domain.dao.ContractMapper;
import pro.bechat.wallet.domain.model.model.Contract;

import java.util.List;

/**
 * create ContractsService by huc
 * 2018/4/15  下午1:23
 */
@Service
public class ContractsService extends BasicService<Contract> {

    @Autowired
    ContractMapper mapper;

    @Override
    protected BasicMapper basicMapper() {

        return mapper;
    }

    public List<Contract> selectContractPending(){

        return  mapper.selectContractPending();
    }

}
