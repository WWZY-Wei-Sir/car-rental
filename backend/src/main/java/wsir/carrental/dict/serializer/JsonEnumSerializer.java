//package wsir.carrental.dict.serializer;
//
//import com.example.chaosdemo.enums.BaseEnum;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.google.common.base.Throwables;
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 返回   "enum": {"code": "2","message": "tom2"} 格式给前端
// */
//
//public class JsonEnumSerializer extends JsonSerializer<BaseEnum> {
//    @Override
//    public void serialize(BaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
//        try {
//            //类的全限定名
//            String name = value.getClass().getName();
//            //类的code值
//            Object code = value.getCode();
//
//            String message = value.getMessage();
//
//            //这里也不用反射了,性能不好,所以直接new一个map再做序列化
//            Map<String, Object> jsonMap = new HashMap<>();
//            jsonMap.put("code", code);
//            jsonMap.put("message", message);
//
//            serializers.defaultSerializeValue(jsonMap, gen);
//        } catch (Exception e) {
//            //  logger.error("JsonEnumSerializer serialize error: " + Throwables.getStackTraceAsString(e));
//            System.out.println("JsonEnumSerializer serialize error: " + Throwables.getStackTraceAsString(e));
//            throw new RuntimeException(e);
//        }
//    }
//}
//
//
//测试：
