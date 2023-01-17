package com.life.square.constants;

//索引库参数
//  这里一般不用
//  一般去es的dev工具那里直接创建，或是那里写好，复制到这里
public class IndexLibraryConstants {
    public static final  String MAPPING_TEMPLATE = "" +
            "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"id\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"diary_id\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "       \"diary_user_id\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "       \"diary_title\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "       \"diary_content\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "       \"diary_category_id\":{\n" +
            "        \"type\": \"integer\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "       \"diary_category_name\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "       \"diary_tags\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "       \"diary_status\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "       \"diary_comment\":{\n" +
            "        \"type\": \"integer\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"diary_views\":{\n" +
            "        \"type\": \"integer\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"diary_love\":{\n" +
            "        \"type\": \"integer\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"enable_comment\":{\n" +
            "        \"type\": \"integer\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"is_deleted\":{\n" +
            "        \"type\": \"integer\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "       \"create_time\":{\n" +
            "        \"type\": \"date\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "       \"update_time\":{\n" +
            "        \"type\": \"date\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \n" +
            "      \n" +
            "       \"person_id\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"person_name\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"sex\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"age\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"image_path\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"disable_number\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"work_addr\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"household_addr\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"marital_status\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"height\":{\n" +
            "        \"type\": \"integer\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"weight\":{\n" +
            "        \"type\": \"integer\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"degree\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"income\":{\n" +
            "        \"type\": \"integer\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"occupation\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"housing_status\":{\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"car_status\":{\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"expected_marry_time\":{\n" +
            "        \"type\": \"date\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"person_intro\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"person_sign\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"wechat\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"wechat_code_images_path\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"qq\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"email\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"phone\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      }\n" +
            "      \n" +
            "    }\n" +
            "  }\n" +
            "}";
}
