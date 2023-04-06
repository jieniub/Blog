package com.ljj.utils;


import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * @source https://github.com/atlassian/commonmark-java
 * @date 19/4/2020 11:56 PM
 */
public class MarkdownUtils {
    /**
     * convert markdown to HTML
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /*
     * add extension [header anchor，create form]
     * Markdown to HTML
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtensions(String markdown) {
        //header id
        Extension extension = HeadingAnchorExtension.create();
        Set<Extension> headingAnchorExtensions = Collections.singleton(extension);
        //table to HTML
        List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtension)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    public AttributeProvider create(AttributeProviderContext context) {
                        return new CustomAttributeProvider();
                    }
                })
                .build();
        return renderer.render(document);
    }

    // handle tag
    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //change tag target to _blank
//            if (node instanceof Link) {
//                attributes.put("target", "_blank");
//            }
            if (node instanceof TableBlock) {
                attributes.put("class", "ui celled table");
            }
        }
    }

    public static void main(String[] args) {
        String b = "### 一、java堆溢出\n" +
                "java堆用于存放对象，所以只要不断创建对象，当对象的总容量触及最大的堆容量就会产生内存溢出异常\n" +
                "##### 1、eclipse对jvm的堆内存进行设置\n" +
                "\n" +
                "\n" +
                "##### 2、java堆内存溢出异常测试\n" +
                "``` css\n" +
                "package OutOfMemoryError;\n" +
                "\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "\n" +
                "public class HeapOOM {\n" +
                "\tstatic class OOMbject{}\n" +
                "\t\n" +
                "\tpublic static void main(String[] args) {\n" +
                "\t\tList<OOMbject> list = new ArrayList<>();\n" +
                "\t\twhile(true) {\n" +
                "\t\t\tlist.add(new OOMbject());\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}\n" +
                "```\n" +
                "##### 3、运行结果\n" +
                "``` css\n" +
                "Exception in thread \"main\" java.lang.OutOfMemoryError: Java heap space\n" +
                "\tat java.util.Arrays.copyOf(Arrays.java:3210)\n" +
                "\tat java.util.Arrays.copyOf(Arrays.java:3181)\n" +
                "\tat java.util.ArrayList.grow(ArrayList.java:267)\n" +
                "\tat java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:241)\n" +
                "\tat java.util.ArrayList.ensureCapacityInternal(ArrayList.java:233)\n" +
                "\tat java.util.ArrayList.add(ArrayList.java:464)\n" +
                "\tat OutOfMemoryError.HeapOOM.main(HeapOOM.java:12)\n" +
                "```\n" +
                "出现java堆溢出时，异常信息“java.lang.OutOfMemoryError:”后面会跟Java heap space\n" +
                "\n" +
                "##### 4、问题解决 \n" +
                " &bull; 首先使用内存映像分析工具对Dump出来的堆储存快照进行分析\\\n" +
                " &bull; 判断导致错误的对象是否是必要的（分清到底是内存泄漏还是内存溢出）\\\n" +
                " &bull; 如果为内存泄漏，进一步用工具查看泄漏对象到GC Roots的引用链，找到泄漏对象无法被垃圾回收的原因，准确定位对象的创建位置，进而找出产生内存泄漏代码的具体位置\\\n" +
                " &bull; 若不是内存泄漏，则检查代码中是否有对象纯在生命周期过长，持有时间过长，储存结构不合理等问题，尽量减少程序运行的消耗\n" +
                "### 二、虚拟机和本地方法栈溢出\n" +
                "##### 1、对java栈容量进行设置：使用-Xss参数对虚拟机栈容量进行设置\n" +
                "##### 2、运行代码\n" +
                "##### 3、运行结果\n" +
                "##### 4、实验结果表明：\n" +
                "    无论是\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n";
        System.out.println(markdownToHtmlExtensions(b));
    }
}
