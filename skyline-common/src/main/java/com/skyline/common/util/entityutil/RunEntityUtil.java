package com.skyline.common.util.entityutil;

/**
 * 类名称： 生成实体、dao、service<br/>
 * 类描述：生成实体、dao、service <br/>
 * 创建人： Z.CJ <br/>
 * 创建时间： 2018年3月23日 下午3:52:43 
 * @updateRemark 修改备注：
 */
public class RunEntityUtil {

        public static void main(String[] args) throws Exception {
                // 包路径 - 必填
                String packagePath = "com.skyline.common.entity";
                // 表名 - 必填
                String tableName = "se_t_bankinfo";
                // 实体名 - 必填
                String aliasName = initcap(tableName);

                // 生成Entity,Mapper,Service,Dao
                new CreateEntityUtil(packagePath, tableName, aliasName);

                // 一次生成所有（不建议使用）
                // new CreateEntityUtil().dbNameEntity(packagePath, "mybatis");
        }

        private static String initcap(String str) {
                char[] ch = str.toCharArray();
                if (ch[0] >= 'a' && ch[0] <= 'z') {
                        ch[0] = (char) (ch[0] - 32);
                }
                for (int i = 0; i < ch.length; i++) {
                        if (ch[i] == '_') {
                                ch[i + 1] = (char) (ch[i + 1] - 32);
                        }
                }
                String sss = new String(ch);
                String[] split = sss.split("_");
                StringBuilder sb = new StringBuilder();
                for (String string : split) {
                        sb.append(string);
                }
                return sb.toString().substring(3);
        }

}
