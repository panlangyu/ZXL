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
        return str.matches("^1((3[0-9])|(4[5|7])|(5([0-3]|[5-9]))|(8[0,1-9]))\\d{8}$");
    }
}
