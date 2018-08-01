package pro.bechat.wallet.publics;

/**
 * @Author ch
 * @Date Create int 2018/7/24 18:16
 * @email 869360026@qq.com
 */
public class PhoneUtils {
    /**
     * true 正确 false 错误
     * @param str
     * @return
     */
    public static boolean isMobile0(String str){
        if(str == null || "".equals(str)){
            return false;
        }
        return str.matches("^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$");
    }
}
