package pro.bechat.wallet.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Transaction;
import pro.bechat.wallet.domain.dao.BasicMapper;
import pro.bechat.wallet.domain.dao.TransactionRecordMapper;
import pro.bechat.wallet.domain.model.model.TransactionRecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * create TransactionRecord by huc
 * 2018/4/13  下午3:35
 */
@Service
public class TransactionRecordService extends BasicService<TransactionRecord> {

    @Autowired
    TransactionRecordMapper mapper;

    @Override
    protected BasicMapper basicMapper() {
        return mapper;
    }

    /**
     * 修改交易确认状态
     */
    private final String STATUS = "-1";


    public void save(Transaction tx) {
        LocalDateTime localDateTime =LocalDateTime.now();
        TransactionRecord record = new TransactionRecord();
        //JSONObject jsonObject = (JSONObject) JSON.toJSON(tx);
        //record = JSONObject.toJavaObject(jsonObject, TransactionRecord.class);
        record.setStatus(STATUS);
        record.setTransactionhash(tx.getHash());
        record.setFromaddress(tx.getFrom());
        record.setToaddress(tx.getTo());
        record.setAmount(String.valueOf(tx.getValue()));
        record.setTransactionid(tx.getHash());
        record.setBlockhash(tx.getBlockHash());
        //record.setBlocknumber(tx.getBlockNumber().to);
        record.setTransactiontime(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        super.save(record);
    }
}
