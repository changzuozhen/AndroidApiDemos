package com.tencent.commontools;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private static final String TAG = "ApplicationTest";

    public ApplicationTest() {
        super(Application.class);
    }

    public void testRegixGroup() throws Exception {
        String poem =
                "Twas brillig, and the slithy toves\n" +
                        "Did gyre and gimble in the wabe.\n" +
                        "All mimsy were the borogoves,\n" +
                        "And the mome raths outgrabe.\n\n" +
                        "Beware the Jabberwock, my son,\n" +
                        "The jaws that bite, the claws that catch.\n" +
                        "Beware the Jubjub bird, and shun\n" +
                        "The frumious Bandersnatch.";
        LogUtils.d(TAG, "testRegix() called with: " + "\n" + poem);
        Matcher m =
                Pattern.compile("(?m)^(\\S+)\\s+((\\S+)\\s+(\\S+)$)")
                        .matcher(poem);
        while (m.find()) {
            for (int j = 0; j <= m.groupCount(); j++)
                LogUtils.d(TAG, "[" + m.group(j) + "]");
            LogUtils.d(TAG, "");
        }
    }

    public void testStartEnd() throws Exception {
        String[] input = new String[]{
                "Java has regular expressions in 1.4",
                "regular expressions now expressing in Java",
                "Java represses oracular expressions"
        };
        Pattern
                p1 = Pattern.compile("re\\w*"),
                p2 = Pattern.compile("Java.*");
        for (int i = 0; i < input.length; i++) {
            LogUtils.d(TAG, "input " + i + ": " + input[i]);
            Matcher
                    m1 = p1.matcher(input[i]),
                    m2 = p2.matcher(input[i]);
            while (m1.find())
                LogUtils.d(TAG, "m1.find() '" + m1.group() + "' start = " + m1.start() + " end = " + m1.end());
            while (m2.find())
                LogUtils.d(TAG, "m2.find() '" + m2.group() + "' start = " + m2.start() + " end = " + m2.end());
            if (m1.lookingAt()) // No reset() necessary
                LogUtils.d(TAG, "m1.lookingAt() start = " + m1.start() + " end = " + m1.end());
            if (m2.lookingAt())
                LogUtils.d(TAG, "m2.lookingAt() start = " + m2.start() + " end = " + m2.end());
            if (m1.matches()) // No reset() necessary
                LogUtils.d(TAG, "m1.matches() start = " + m1.start() + " end = " + m1.end());
            if (m2.matches())
                LogUtils.d(TAG, "m2.matches() start = " + m2.start() + " end = " + m2.end());
        }

/*
"input 0: Java has regular expressions in 1.4",
"m1.find() 'regular' start = 9 end = 16",
"m1.find() 'ressions' start = 20 end = 28",
"m2.find() 'Java has regular expressions in 1.4'" +
        " start = 0 end = 35",
"m2.lookingAt() start = 0 end = 35",
"m2.matches() start = 0 end = 35",
"input 1: regular expressions now " +
        "expressing in Java",
"m1.find() 'regular' start = 0 end = 7",
"m1.find() 'ressions' start = 11 end = 19",
"m1.find() 'ressing' start = 27 end = 34",
"m2.find() 'Java' start = 38 end = 42",
"m1.lookingAt() start = 0 end = 7",
"input 2: Java represses oracular expressions",
"m1.find() 'represses' start = 5 end = 14",
"m1.find() 'ressions' start = 27 end = 35",
"m2.find() 'Java represses oracular expressions' " +
        "start = 0 end = 35",
"m2.lookingAt() start = 0 end = 35",
"m2.matches() start = 0 end = 35"
*/

    }

    public void testReFlags() throws Exception {
        Pattern p = Pattern.compile("^java",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher m = p.matcher(
                "Java has regex\njAva has regex\n" +
                        "jaVa has pretty good regular expressions\n" +
                        "Regular expressions are in javA");
        while (m.find())
            LogUtils.d(TAG, m.group());

    }

    public void testSplit() throws Exception {
        String input =
                "This!!unusual use!!of exclamation!!points";
        LogUtils.d(TAG, Arrays.asList(Pattern.compile("!!").split(input)).toString());
// Only do the first three:
        LogUtils.d(TAG, Arrays.asList(Pattern.compile("!!").split(input, 3)).toString());
        LogUtils.d(TAG, Arrays.asList("Aha! String has a split() built in!" .split(" ")).toString());
/*
[This, unusual use, of exclamation, points]
[This, unusual use, of exclamation!!points]
[Aha!, String, has, a, split(), built, in!]
 */
    }

    public void testReplacement() throws Exception {
        String s = ";zd';\n" +
                "/*! Here's a block of text to use as input to\n" +
                "    the regular expression matcher. Note that we'll\n" +
                "    first extract the block of text by looking for\n" +
                "    the special delimiters, then process the\n" +
                "    extracted block. !*/\n" +
                "    alsdj\n;lsjfd";
// Match the specially-commented block of text above:
        Matcher mInput =
                Pattern.compile("/\\*!(.*)!\\*/", Pattern.DOTALL)
                        .matcher(s);
        LogUtils.d(TAG, s);


        if (mInput.find()) {
            s = mInput.group(1); // Captured by parentheses
            LogUtils.d(TAG, "group(1)");
            LogUtils.d(TAG, s);
        }

// Replace two or more spaces with a single space:
        s = s.replaceAll(" {2,}", " ");
        LogUtils.d(TAG, "Replace two or more spaces with a single space");

// Replace one or more spaces at the beginning of each
// line with no spaces. Must enable MULTILINE mode:
        s = s.replaceAll("(?m)^ +", "");
        LogUtils.d(TAG, "Replace one or more spaces at the beginning of each");
        LogUtils.d(TAG, s);

        s = s.replaceFirst("[aeiou]", "(VOWEL1)");
        LogUtils.d(TAG, "replaceFirst(\"[aeiou]\", \"(VOWEL1)\")");
        LogUtils.d(TAG, s);

        StringBuffer sbuf = new StringBuffer();
        Pattern p = Pattern.compile("[aeiou]");
        Matcher m = p.matcher(s);
// Process the find information as you
// perform the replacements:
        while (m.find())
            m.appendReplacement(sbuf, m.group().toUpperCase());
// Put in the remainder of the text:
        m.appendTail(sbuf);
        LogUtils.d(TAG, sbuf.toString());

    }

    public void testResetting() throws Exception {
        Matcher m = Pattern.compile("[frb][aiu][gx]")
                .matcher("fix the rug with bags");
        while (m.find())
            LogUtils.d(TAG, m.group());
        m.reset("fix the rig with rags");
        while (m.find())
            LogUtils.d(TAG, m.group());

    }

    public void testMatcherTest() throws Exception {


        //该例将把句子里的"Kelvin"改为"Kevin"

        //生成Pattern对象并且编译一个简单的正则表达式"Kelvin"
        Pattern p = Pattern.compile("Kelvin");
        //用Pattern类的matcher()方法生成一个Matcher对象
        Matcher m = p.matcher("Kelvin Li and Kelvin Chan are both working in Kelvin Chen's KelvinSoftShop company");
        StringBuffer sb = new StringBuffer();
        int i = 0;
        //使用find()方法查找第一个匹配的对象
        boolean result = m.find();
        //使用循环将句子里所有的kelvin找出并替换再将内容加到sb里
        while (result) {
            i++;
            m.appendReplacement(sb, "Kevin");
            LogUtils.d(TAG, "第" + i + "次匹配后sb的内容是：" + sb);
            //继续查找下一个匹配对象
            result = m.find();
        }
        //最后调用appendTail()方法将最后一次匹配后的剩余字符串加到sb里；
        m.appendTail(sb);
        LogUtils.d(TAG, "调用m.appendTail(sb)后sb的最终内容是:" + sb.toString());

    }

    public void testGroup() throws Exception {
        Pattern p = Pattern.compile("(ca)(t)");
        Matcher m = p.matcher("one cat,two cats in the yard");
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        int j = 0;
        while (result) {
            j++;
            LogUtils.d(TAG, "第" + j + "次查找获得匹配组的数量为：" + m.groupCount());
            for (int i = 1; i <= m.groupCount(); i++) {
                LogUtils.d(TAG, "第" + i + "组的子串内容为： " + m.group(i));
            }
            result = m.find();

        }
    }

    public void testCheckEmail() throws Exception {
        //(?m)(^\.|^\@)|(^www\.)|([^A-Za-z0-9\.\@_\-~#]+)
//        www.kevin@163.net
//        @kevin@163.net
//        cgjmail#$%@163.net
        String input =
//                "www.kevin@163.net\n";
//                "@kevin@163.net\n";
                "cgjmail#$%@163.net";
        //检测输入的EMAIL地址是否以 非法符号"."或"@"作为起始字符
        Pattern p = Pattern.compile("^\\.|^\\@");
        Matcher m = p.matcher(input);
        if (m.find()) {
            System.err.println("EMAIL地址不能以'.'或'@'作为起始字符");
        }
        //检测是否以"www."为起始
        p = Pattern.compile("^www\\.");
        m = p.matcher(input);
        if (m.find()) {
            LogUtils.d(TAG, "EMAIL地址不能以'www.'起始");
        }
        //检测是否包含非法字符
        p = Pattern.compile("[^A-Za-z0-9\\.\\@_\\-~#]+");
        m = p.matcher(input);
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        boolean deletedIllegalChars = false;
        while (result) {
            //如果找到了非法字符那么就设下标记
            deletedIllegalChars = true;
            //如果里面包含非法字符如冒号双引号等，那么就把他们消去，加到SB里面
            m.appendReplacement(sb, "");
            result = m.find();
        }
        m.appendTail(sb);
        input = sb.toString();
        if (deletedIllegalChars) {
            LogUtils.d(TAG, "输入的EMAIL地址里包含有冒号、逗号等非法字符，请修改");
            LogUtils.d(TAG, "您现在的输入为: " + input);
            LogUtils.d(TAG, "修改后合法的地址应类似: " + input);
        }

    }
}