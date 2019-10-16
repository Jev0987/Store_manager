package com.jevin.dao;

import com.jevin.pojo.ApplyEnter;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;

/*
 *  @项目名：  Store_manager 
 *  @包名：    com.jevin.dao
 *  @文件名:   CommonRepository
 *  @创建者:   85169
 *  @创建时间:  2019/9/11 20:30
 *  @描述：    根据对象来生成sql
 */
@Repository
public class CommonRepository<T> {
    //获取对象信息，返回 sql语句
    public StringBuffer getFiledValues(T t,int pagenum) throws IllegalAccessException {
        /**.substring()  返回字符串的子字符串
         *语法：substring(int beginIndex)   或    substring(int beginIndex , int endIndex)    起始索引，结束索引
         **/

        /**语法：lasIndexOf()
         * 四种：lasIndexOf(int ch)    lasIndexOf(int ch,int fromIndex)    lasIndexOf(String str)    lasIndexOf(String str, int fromIndex)
         * lastIndexOf(String str): 返回指定字符在此字符串中最后一次出现处的索引，如果此字符串中没有这样的字符，则返回 -1。
         */
        String table = t.getClass().toString().substring(t.getClass().toString().lastIndexOf(".")+1);
        StringBuffer stringBuffer = new StringBuffer("SELECT * FROM " + table + " WHERE " );
        StringBuffer sql = new StringBuffer();

        if (t!=null){
            Class tClass = t.getClass();
            /**Field是一个类，位于java.lang.reflect包下，在java反射中Field类描述的是类的属性信息，功能包括：
             *获取当前对象的成员变量的类型
             *对成员变量重新设值
             **/
            //getDeclaredFields()获取类中声明的所有的字段（属性）(public、protected、default、private),但不包括继承的属性，返回Field对象的一个数组
            Field[] fields = tClass.getDeclaredFields();
            int count = 0;
            for (int i=0; i<fields.length;i++){
                String buf = "";
                //Java的反射机制提供的setAccessible()方法可以取消java的权限控制检查   private属性。
                fields[i].setAccessible(true);
                //判断返回指定对象t上此Field表示的字段的值是否为空
                if (fields[i].get(t)!=null){
                    //  getType()用来返回Class对象
                    //  判断field[i]内的类型是int or string  如果是int，判断是否count==0，如果是 在buf加入首块字段，如果不是，选择AND的那句加入。
                    // 如果是string,判断count，选择是否需要选择AND的那句
                    if (fields[i].getType().toString().equals("int") && fields[i].getInt(t)!=0){
                        if (count==0){
                            buf = fields[i].getName() + " LIKE " + "'%" + fields[i].get(t) +"%'";
                        }else {
                            buf += " AND ";
                            buf += fields[i].getName() +" LIKE " + "'%" + fields[i].get(t) +"%'";
                        }
                        sql.append(buf);
                        count++;
                    }else if(fields[i].getType().toString().equals("class java.lang.String")){
                        if (count == 0){
                            buf = fields[i].getName() + " LIKE " + "'%" + fields[i].get(t) + "%'";
                        }else {
                            buf += " AND ";
                            buf += fields[i].getName() + " LIKE " + "'%" + fields[i].get(t) + "%'";
                        }
                        sql.append(buf);
                        count++;
                    }
                }
            }
            stringBuffer.append(sql);
        }else {

            return new StringBuffer("");
        }

        return stringBuffer;
    }

    public static void main(String [] args){
        CommonRepository<ApplyEnter> commonRepository = new CommonRepository();
        ApplyEnter applyEnter = new ApplyEnter();
        applyEnter.setStatus("已确认");
        applyEnter.setApplyPersonId("admin");
        try {
            System.out.println(commonRepository.getFiledValues(applyEnter , 2));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}


