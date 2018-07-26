package pro.bechat.wallet.domain.service;

import org.omg.SendingContext.RunTimeOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.bechat.wallet.domain.dao.BasicMapper;
import pro.bechat.wallet.domain.dao.CodeMapper;
import pro.bechat.wallet.domain.model.model.Code;
import pro.bechat.wallet.publics.HttpUtils;
import pro.bechat.wallet.publics.PhoneUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author ch
 * @Date Create int 2018/7/24 17:24
 * @email 869360026@qq.com
 */
@Service
public class CodeService extends BasicService<CodeMapper> {
    @Autowired
    CodeMapper codeMapper;

    @Override
    protected BasicMapper basicMapper() {
        return codeMapper;
    }

    /**
     * 获得验证码
     */
    public void getCode(String phone, int lenth) throws Exception {

        //生成随机数字
        String strRandom = getRandom(lenth);

        //开始请求短信平台
        Map<String, String> params = new HashMap<>();
        params.put("name", "13713644886");
        params.put("pwd", "308D04D8AFE0E78EA47D99851DC4");
        params.put("content", String.format("【VAHWallet】您的验证码是%s，请在5分钟内使用。如非本人操作，请忽略本短信。", strRandom));
        params.put("mobile", phone);
        params.put("stime", "");
        params.put("sign", "VAHWalle");
        params.put("type", "pt");
        String strResult = null;
        try {
            strResult = HttpUtils.post("http://web.duanxinwang.cc/asmx/smsservice.aspx", params);
        } catch (Exception e) {
            throw new Exception("发送验证码失败");
        } finally {
            //解析请求数据并插入到数据库中
            //插入数据库
            Code code = new Code();
            code.setTel(phone);
            code.setContent(strRandom);
            if (strResult != null) {
                String[] data = strResult.split(",");
                if (data[0].equals("0")) {
                    code.setSendid(data[1]);
                    code.setStatus(0);
                } else {
                    code.setSendid("发送失败，系统原因");
                    code.setStatus(1);
                }
            } else {
                code.setSendid("发送短信失败，短信平台原因");
                code.setStatus(1);
            }

            codeMapper.insert(code);
        }
    }


    /**
     * 校验验证码
     * @param phone 手机号码
     * @param code 验证码
     */
    public void checkCode(String phone,String code) throws Exception {
        if(!PhoneUtils.isMobile0(phone)){
            throw new Exception("请输入正确的时候机号码");
        }
        if(null == code || "".equals(code)){
            throw new Exception("请输入正确的验证码");
        }
        //1查询最后一条与用户请求进行比对。
        Code selectCode = codeMapper.lastSmsCode(phone);
        if(null == selectCode){
            throw new Exception("您还没有获取过验证码");
        }

        if(code.equals(selectCode.getContent())){
            //输入验证码与数据库存储相同的时候
            if(selectCode.getStatus() == Code.STATUS_CAN_USE){
                //校验成功
                codeMapper.update_status(selectCode.getId());
                return;
            }else{
                throw  new Exception("验证码已经被使用");
            }
        }else{
            throw new Exception("您输入的验证码有误");
        }
    }

    private String getRandom(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }

}
