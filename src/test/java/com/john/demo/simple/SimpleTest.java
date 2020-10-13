package com.john.demo.simple;

import org.junit.Test;

/**
 * 一些简单API的测试
 */
public class SimpleTest {

    /**
     * 考察：栈内存、堆内存
     * 参考资料：
     * https://juejin.im/post/6844903977646030855
     */
    @Test
    public void testStr () {

        class MyString {
            private String str;
            public MyString(String str) {
                this.str = str;
            }
            @Override
            public String toString() {
                return this.str;
            }

            public void setStr(String str) {
                this.str = str;
            }
        }

        class TestStr {
            void changeStr(String str) {
                str = "新字符串";
            }

            void changeStr(MyString str) {
                str = new MyString("新字符串");
            }

            void changeStr1(MyString str) {
                str.setStr("新字符串");
            }
        }

        // A
        String a = "abc1";
        new TestStr().changeStr(a);
        System.out.println("strA: " + a);

        // B
        MyString b = new MyString("abc2");
        new TestStr().changeStr(b);
        System.out.println(b);

        // C
        MyString c = new MyString("abc3");
        new TestStr().changeStr1(c);
        System.out.println(c);

    }

}
