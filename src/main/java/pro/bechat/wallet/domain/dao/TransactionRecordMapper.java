package pro.bechat.wallet.domain.dao;

import org.springframework.stereotype.Repository;
import pro.bechat.wallet.domain.model.model.TransactionRecord;

@Repository
public interface TransactionRecordMapper extends BasicMapper<TransactionRecord>{
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WLT_T_TRANSACTIONRECORD
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(TransactionRecord key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WLT_T_TRANSACTIONRECORD
     *
     * @mbggenerated
     */
    int insert(TransactionRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WLT_T_TRANSACTIONRECORD
     *
     * @mbggenerated
     */
    int insertSelective(TransactionRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WLT_T_TRANSACTIONRECORD
     *
     * @mbggenerated
     */
    TransactionRecord selectByPrimaryKey(TransactionRecord key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WLT_T_TRANSACTIONRECORD
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TransactionRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WLT_T_TRANSACTIONRECORD
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TransactionRecord record);
}