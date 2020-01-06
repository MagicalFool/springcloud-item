package item.common.base;

public class BaseApiService {

    private ResponseBase responseBase;

    public ResponseBase reResult(Integer code, String message, Object data) {
        return new ResponseBase(code, message, data);
    }

    public ResponseBase reSuccessNoData(Integer code, String message) {
        return reResult(code, message, null);
    }

    public ResponseBase reSuccessNoData() {
        return reSuccessNoData(CodeConstants.HTTP_RES_CODE_200, CodeConstants.HTTP_RES_CODE_200_VALUE);
    }

    public ResponseBase reSuccess(Object data) {
        return reResult(CodeConstants.HTTP_RES_CODE_200, CodeConstants.HTTP_RES_CODE_200_VALUE, data);
    }

    public ResponseBase reFail(String message){
        return reResult(CodeConstants.HTTP_RES_CODE_500,message,null);
    }


}
