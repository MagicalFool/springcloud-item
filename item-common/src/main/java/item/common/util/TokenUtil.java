package item.common.util;

import item.common.base.CodeConstants;

import java.util.UUID;

public class TokenUtil {
    public static String getToken(){
        return CodeConstants.TOKEN_MEMBER + UUID.randomUUID();
    }
}
