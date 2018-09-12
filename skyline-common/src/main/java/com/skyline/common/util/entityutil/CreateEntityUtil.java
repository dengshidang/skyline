package com.skyline.common.util.entityutil;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

/**
 * 类名称： 数据实体生成工具类<br/>
 * 类描述： <br/>
 * 创建人： lishanglong <br/>
 * 
 * @updateRemark 修改备注：
 */
public class CreateEntityUtil {

        private String[] colorinames; // 原始级数

        private String[] colnames; // 列名数组

        private String[] columnComments; // 列表注释

        private String[] colTypes; // 列名类型数组

        private int[] colSizes; // 列名大小数组

        private String tbaleComment;// 表注释

        private boolean f_util = false; // 是否需要导入包java.util.*

        private boolean f_sql = false; // 是否需要导入包java.sql.*

        public CreateEntityUtil() {

        }

        class FileValue {
                String fileName;

                String fileContent;

                FileValue(String fileName, String fileContent) {
                        this.fileName = fileName;
                        this.fileContent = fileContent;
                }
        }

        /**
         * @param packagePath 包路径
         * @param tableName 表名
         * @param alias 生成实体别命
         * @throws Exception
         */
        public CreateEntityUtil(String packagePath, String tableName, String alias) throws Exception {
                // DatabaseUtils.init();
                Connection conn = DatabaseUtils.getConnection(); // 得到数据库连接
                PreparedStatement pstmt = null;
                String strsql = "select * from " + tableName;
                try {

                        pstmt = conn.prepareStatement(strsql);
                        ResultSetMetaData rsmd = pstmt.getMetaData();
                        ResultSet rss = pstmt
                                        .executeQuery("select table_comment from information_schema.tables  where  table_name ='"
                                                        + tableName + "'");
                        while (rss.next()) {
                                tbaleComment = rss.getString(1);
                        }

                        ResultSet rs = pstmt.executeQuery("show full columns from " + tableName);
                        int size = rsmd.getColumnCount(); // 共有多少列
                        colorinames = new String[size];
                        columnComments = new String[size];
                        colnames = new String[size];
                        colTypes = new String[size];
                        colSizes = new int[size];
                        for (int i = 0; i < rsmd.getColumnCount() && rs.next(); i++) {
                                colorinames[i] = rsmd.getColumnName(i + 1);
                                
                                // colnames[i] = this.getCamelStr(rsmd.getColumnName(i + 1));
                                // 表的属性名称是什么实体的属性名称就是什么
                                colnames[i] = rsmd.getColumnName(i + 1);
                                
                                
                                colTypes[i] = rsmd.getColumnTypeName(i + 1);
                                columnComments[i] = rs.getString("Comment");
                                if (colTypes[i].equalsIgnoreCase("datetime")
                                                || colTypes[i].equalsIgnoreCase("date")) {
                                        f_util = true;
                                }
                                if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")) {
                                        f_sql = true;
                                }
                                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
                        }
                        Map<String, FileValue> fileMap = new HashMap<String, FileValue>();
                        try {
                                // 文本
                        	
                                parse(colnames, colTypes, colSizes, packagePath, tableName, alias,
                                                "src/main/java", fileMap);
                                
                                
                                
                                // 此处不需要
                                // parseMapper(colorinames,colnames,colTypes,packagePath,alias,"resource/mapper",fileMap);
                                
                                
                              //  parseInterface(packagePath, alias, "business", "src/main/java/", fileMap);
                                //parseInterfaceImpl(packagePath, alias, "business", "src/main/java/", fileMap);
                                parseInterface(packagePath, alias, "mapper", "src/main/java/", fileMap);
                                
                                
                                // 此处不需要
                                // parseInterfaceImpl(packagePath, alias,
                                // "repository","src",fileMap);
                                if (fileMap != null && !fileMap.isEmpty()) {
                                        // 创建文件夹
                                        for (String filePath : fileMap.keySet()) {
                                                File file = new File(filePath);
                                                if (!file.exists()) {
                                                        file.mkdirs();
                                                }
                                        }
                                        // 创建文件
                                        for (Entry<String, FileValue> fileEntry : fileMap.entrySet()) {
                                                String fileName = fileEntry.getKey() + File.separator
                                                                + fileEntry.getValue().fileName;
                                                FileUtils.writeStringToFile(new File(fileName),
                                                                fileEntry.getValue().fileContent);
                                        }
                                }
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        DatabaseUtils.closeDatabase(conn, pstmt, null);
                }
        }

        /**
         * 转换路径
         * 
         * @param packagePath 路径
         * @param packageName 替换包名
         * @param sourcePath 源路径
         * @return
         */
        private String parsePath(String packagePath, String packageName, String sourcePath) {
                if (!StringUtils.isEmpty(packageName)) {
                        packagePath = packagePath.replace("entity", packageName);
                }
                return System.getProperty("user.dir") + "/" + sourcePath + "/"
                                + packagePath.replaceAll("\\.", "/");
        }

        /**
         * 解析处理(生成接口类)
         */
        private String parseInterface(String packagePath, String alias, String packageName, String sourcePath,
                        Map<String, FileValue> fileMap) {
                String filePath = parsePath(packagePath, packageName, sourcePath);
                String entityName = alias + initcap(packageName) + ".java";
                String servicePackage = packagePath.replace("entity", packageName);
                String aliasType = alias + ", Integer";
                StringBuffer sb = new StringBuffer();
                sb.append("package " + servicePackage + ";\r\n\r\n");
                // 注入Enitty所需包
                sb.append("import ").append(packagePath + "." + alias).append(";\r\n");
                // 注入基类所需包
                if ("business".equals(packageName)) {
                        // service
                        sb.append("\n");
                        sb.append("public interface " + alias + initcap(packageName) + " extends BaseBusiness<"
                                        + aliasType + ">{\r\n\r\n");
                } else {
                        // Mapper
                        sb.append("import com.skyline.common.util.MyMapper;\r\n\r\n");
                        sb.append("public interface " + initcap(alias) + initcap(packageName) + " extends MyMapper<"
                                        + alias + ">{\r\n\r\n");
                }
                sb.append("\r\n");
                sb.append("}\r\n");
                System.out.println(sb.toString());
                FileValue fileValue = new FileValue(entityName, sb.toString());
                fileMap.put(filePath, fileValue);
                return sb.toString();
        }

        /**
         * 解析处理(生成接口实现类)
         */
        private String parseInterfaceImpl(String packagePath, String alias, String packageName, String sourcePath,
                        Map<String, FileValue> fileMap) {
                String entityName = alias + initcap(packageName) + "Impl.java";
                String filePath = parsePath(packagePath, packageName + ".impl", sourcePath);
                String entityPackage = packagePath;
                String servicePackage = packagePath.replace("entity", packageName);
                String serviceImplPackage = packagePath.replace("entity", packageName + ".impl");
                String interfaceName = alias + initcap(packageName);
                String autoName = "", extendsName = "", aliasType = alias + ", Integer";
                StringBuffer sb = new StringBuffer();
                sb.append("package " + serviceImplPackage + ";\r\n\r\n");
                if (packageName.equalsIgnoreCase("business")) {
                        autoName = "@Service";
                        extendsName = "extends BaseBusinessImpl<" + aliasType + "> implements " + interfaceName;
                        // 注入@service所需包
                        sb.append("import org.springframework.stereotype.Service;\r\n");
                        // 注入实体类
                        sb.append("import ").append(entityPackage).append(".").append(alias).append(";\n");
                        // 注入接口类
                        sb.append("import ").append(servicePackage).append(".").append(interfaceName)
                                        .append(";\n\n");

                        sb.append(autoName).append("\n");
                        sb.append("public class " + alias + initcap(packageName) + "Impl " + extendsName
                                        + "{\r\n\r\n");

                        // 可加方法

                        sb.append("}\r\n");
                        System.out.println(sb.toString());
                        FileValue fileValue = new FileValue(entityName, sb.toString());
                        fileMap.put(filePath, fileValue);
                }
                return sb.toString();
        }

        /**
         * 解析处理(生成实体类主体代码)
         */
        private String parse(String[] colNames, String[] colTypes, int[] colSizes, String packagePath,
                        String tableName, String alias, String sourcePath, Map<String, FileValue> fileMap) {
                // 路径
                String entityName = alias + ".java";
                String filePath = parsePath(packagePath, null, sourcePath);
                StringBuffer sb = new StringBuffer();
                sb.append("package " + packagePath + ";\r\n\r\n");
                if (f_util) {
                        sb.append("import java.util.Date;\r\n");
                }
                if (f_sql) {
                        sb.append("import java.sql.*;\r\n\r\n\r\n");
                }
                // 注入spring dataJpa实体所需类
                sb.append("import java.io.Serializable;\r\n");
                sb.append("import javax.persistence.GeneratedValue;\r\n");
                sb.append("import javax.persistence.GenerationType;\r\n");

                sb.append("import javax.persistence.Column;\r\n");
                sb.append("import javax.persistence.Entity;\r\n");
                sb.append("import javax.persistence.Id;\r\n");
                sb.append("import javax.persistence.Table;\r\n");
                sb.append("\r\n");
                // 类注释
                sb.append("/**\r\n");
                sb.append(" * 表注释： " + tbaleComment + " <br/>\r\n");
                sb.append(" * 类描述： " + tbaleComment + " <br/>\r\n");
                sb.append(" * 创建人： 工具类初始创建 <br/>\r\n");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String ddtime = sdf.format(new Date());
                sb.append(" * 创建时间：" + ddtime + "\r\n");
                sb.append(" * \r\n");
                sb.append(" * @updateRemark 修改备注：\r\n");
                sb.append(" */\r\n");
                // 注入表头
                sb.append("@Entity\r\n");
                sb.append("@Table(name = \"" + tableName + "\")\r\n");
                // sb.append("@EntityListeners(AuditingEntityListener.class)\r\n");
                sb.append("public class " + alias + " implements Serializable {\r\n\r\n");
                processAllAttrs(sb);
                sb.append("\r\n");
                processAllMethod(sb);
                sb.append("}\r\n");
                System.out.println(sb.toString());
                FileValue fileValue = new FileValue(entityName, sb.toString());
                fileMap.put(filePath, fileValue);
                return sb.toString();

        }

        /**
         * 解析处理生成Mapper
         */
        @SuppressWarnings("unused")
        private String parseMapper(String[] oricolNames, String[] colNames, String[] colTypes, String packagePath,
                        String alias, String sourcePath, Map<String, FileValue> fileMap) {
                String entityName = alias + "Mapper.xml";
                String filePath = parsePath("", "", sourcePath);
                String classPath = packagePath + "." + alias;
                StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                sb.append("\n");
                sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
                sb.append("\n");
                sb.append("<mapper namespace=\"" + alias + "\">");
                sb.append("\n\r");
                sb.append("<resultMap id=\"BaseResultMap\" type=\"" + classPath + "\">").append("\n");
                StringBuffer baseColumnList = new StringBuffer();
                // 列表
                for (int i = 0; i < oricolNames.length; i++) {
                        String column = oricolNames[i];
                        String property = colNames[i];
                        String jdbcType = sqlType2MapperType(colTypes[i]);
                        if ("datetime".equalsIgnoreCase(colTypes[i]) || "date".equalsIgnoreCase(colTypes[i])) {
                                baseColumnList.append("to_char(" + column + ",'YYYY-MM-DD hh24:mm:ss') ")
                                                .append(column).append(",");
                        } else {
                                baseColumnList.append(column).append(",");
                        }
                        if ("ID".equalsIgnoreCase(column)) {
                                sb.append("<id jdbcType=\"" + jdbcType + "\" column=\"" + column
                                                + "\" property=\"" + property + "\" />").append("\n");
                                continue;
                        }
                        sb.append("<result jdbcType=\"" + jdbcType + "\" column=\"" + column + "\" property=\""
                                        + property + "\" />").append("\n");
                }
                // 删除逗号
                baseColumnList.deleteCharAt(baseColumnList.length() - 1);
                sb.append("</resultMap>").append("\n");
                sb.append("<sql id=\"Base_Column_List\">").append("\n");
                sb.append(baseColumnList).append("\n").append("</sql>").append("\n").append("</mapper>");
                System.out.println(sb.toString());
                FileValue fileValue = new FileValue(entityName, sb.toString());
                fileMap.put(filePath, fileValue);
                return sb.toString();

        }

        /**
         * 生成所有的方法(暂时不使用)
         * 
         * @param sb
         */
        private void processAllMethod(StringBuffer sb) {
                for (int i = 0; i < colnames.length; i++) {
                        sb.append("\tpublic void set" + initcap(colnames[i]) + "(" + sqlType2JavaType(colTypes[i])
                                        + " " + colnames[i] + "){\r\n");
                        sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
                        sb.append("\t}\r\n\r\n");

                        sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initcap(colnames[i])
                                        + "(){\r\n");
                        sb.append("\t\treturn " + colnames[i] + ";\r\n");
                        sb.append("\t}\r\n\r\n");
                }
        }

        /**
         * 解析输出属性
         * 
         * @return
         */
        private void processAllAttrs(StringBuffer sb) {
                sb.append("\tprivate static final long serialVersionUID = 1L;\r\n\r\n");
                for (int i = 0; i < colnames.length; i++) {
                        String colname = colnames[i];
                        // 写入注释
                        sb.append("\t/** " + columnComments[i] + "*/\r\n");
                        if (i == 0) {
                                sb.append("\t@Id\r\n");
                                /*sb.append("\t@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "
                                                + "\"SELECT REPLACE(UUID(), '-', '')\"" + ")\r\n");*/
                                sb.append("\t@GeneratedValue(strategy = GenerationType.IDENTITY)\r\n");
                        }
                        if ("createTime".equals(colname)) {
                                // sb.append("\t@CreatedDate\r\n");
                        }
                        if ("updateTime".equals(colname)) {
                                // sb.append("\t@LastModifiedDate\r\n");
                        }
                        sb.append("\t").append("@Column(name = \"" + colorinames[i] + "\")\r\n");
                        if ("createTime".equals(colname) || "updateTime".equals(colname)) {
                                // sb.append("\t@Temporal(TemporalType.TIMESTAMP)\r\n");
                        }
                        sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + colname + ";\r\n");
                        sb.append("\r\n");
                }
        }

        /**
         * 把输入字符串的首字母改成大写
         * 
         * @param str
         * @return
         */
        private String initcap(String str) {
                char[] ch = str.toCharArray();
                if (ch[0] >= 'a' && ch[0] <= 'z') {
                        ch[0] = (char) (ch[0] - 32);
                }
                return new String(ch);
        }

        // 首字母转小写
        public String initLow(String s) {
                if (Character.isLowerCase(s.charAt(0)))
                        return s;
                else
                        return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0)))
                                        .append(s.substring(1)).toString();
        }

        // 例：user_name --> userName
        private String getCamelStr(String s) {
                s = s.toLowerCase();
                while (s.indexOf("_") > 0) {
                        int index = s.indexOf("_");
                        // System.out.println(s.substring(index+1, index+2).toUpperCase());
                        s = s.substring(0, index).toLowerCase() + s.substring(index + 1, index + 2).toUpperCase()
                                        + s.substring(index + 2);
                }
                return s;
        }

        // 时间格式也采用字符串，方便解释
        private String sqlType2JavaType(String sqlType) {
                if (sqlType.equalsIgnoreCase("bit")) {
                        return "Bool";
                } else if (sqlType.equalsIgnoreCase("tinyint")) {
                        return "Byte";
                } else if (sqlType.equalsIgnoreCase("smallint")) {
                        return "Short";
                } else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("integer")) {
                        return "Integer";
                } else if (sqlType.equalsIgnoreCase("bigint")) {
                        return "Long";
                } else if (sqlType.equalsIgnoreCase("float")) {
                        return "Float";
                } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                                || sqlType.equalsIgnoreCase("real")) {
                        return "Double";
                } else if (sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("smallmoney")) {
                        return "Double";
                } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                                || sqlType.equalsIgnoreCase("number") || sqlType.equalsIgnoreCase("varchar2")) {
                        return "String";
                } else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("date")) {
                        return "Date";
                } else if (sqlType.equalsIgnoreCase("image")) {
                        return "Blob";
                } else if (sqlType.equalsIgnoreCase("text")) {
                        return "Clob";
                }
                return null;
        }

        private String sqlType2MapperType(String sqlType) {
                return "VARCHAR";
        }
        
        /**
         * 给数据库名称和包名一次生成所有实体、mapper、service
         * @param packagePath 包路径 - 必填
         * @param dbName 数据库名称 - 必填
         * @throws Exception
         * @author Z.CJ
         * @date 2018年3月2日
         */
        public void dbNameEntity(String packagePath,String dbName) throws Exception{
                // 表名 
                String tableName = null;
                // 实体名 
                String aliasName = null;
                //******
                Connection conn = DatabaseUtils.getConnection(); // 得到数据库连接
                PreparedStatement pstmt = null;
                String strsql = "select * from " + tableName;
                try {
                        pstmt = conn.prepareStatement(strsql);
                        ResultSet rss = pstmt
                                        .executeQuery("select table_name from information_schema.TABLES where TABLE_SCHEMA='" + dbName + "'");
                        CreateEntityUtil c = new CreateEntityUtil();
                        while (rss.next()) {
                                tableName = rss.getString(1);
                             
                                //String camelStr = c.getCamelStr(tableName);
                                String camelStr = tableName;
                                
                                aliasName = c.initcap(camelStr) + "Entity";
                                // 生成Entity,Mapper,Service
                                new CreateEntityUtil(packagePath, tableName, aliasName);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        DatabaseUtils.closeDatabase(conn, pstmt, null);
                }
        }
        
}
