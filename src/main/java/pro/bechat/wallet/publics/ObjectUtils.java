package pro.bechat.wallet.publics;

import pro.bechat.wallet.domain.model.model.Dictionary;

import java.util.UUID;

/**
 * 处理业务冗余代码
 */
public class ObjectUtils {


    /**
     * 处理转账信息的备注, 如果没填写备注信息的话,分配类型名称
     * @param remark
     * @param typeCode
     * @return
     */
    public static String getWalletRemark(String remark,Integer typeCode){

        if(remark == null || remark.equals("")){
            switch (typeCode){

                case 1:
                    remark = "转入";
                    break;
                case 2:
                    remark = "转出";
                    break;
                case 6:
                    remark = "扣取手续费";
                    break;
                default:
                    remark = "只有以上三种类型";
                    break;
            }
        }

        return remark;
    }


    /**
     * 处理转账信息的备注, 如果没填写备注信息的话,分配类型名称
     * @param remark
     * @param typeCode
     * @return
     */
    public static String getRemark(String remark,Integer typeCode){

        if(remark != null && !remark.equals("")){

         /*   if(typeCode == 1){
                remark = "转入";
            }*/
            if(typeCode == 2){
                remark = "转出";
            }
            return remark;
        }


        if(remark == null || remark.equals("")){
            switch (typeCode){

                case 1:
                    remark = "转入,冻结(freeAmount)增加,可用(availableAmount)减少,本金不变";
                    break;
                case 2:
                    remark = "转出,冻结(freeAmount)减少,可用(availableAmount)增加,本金不变";
                    break;
                case 3:
                    remark = "直推";
                    break;
                case 4:
                    remark = "利息";
                    break;
                case 5:
                    remark = "团队奖";
                    break;
                case 6:
                    remark = "扣取手续费";
                    break;
                default:
                    remark = "只有以上六种类型";
                    break;
            }

        }

        return remark;
    }


    /**
     * 生成UUID
     * @return
     */
    public static String getUUID(){

        String uuid = UUID.randomUUID().toString().replaceAll("-","");

        return uuid;
    }



}
