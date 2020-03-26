package com.study.suanfa;

/**
 * @author ldb
 * @date 2019-12-04 16:01
 */
public class RegularExpression {
    private boolean matched = false;
    // 正则表达式
    private char[] pattern;
    // 正则表达式长度
    private int len;

    public RegularExpression(char[] pattern, int len) {
        this.pattern = pattern;
        this.len = len;
    }

    public boolean match(char[] text, int tlen) {
        matched = false;
        rmatch(0, 0, text, tlen);
        return matched;
    }

    private void rmatch(int ti, int pj, char[] text, int tlen) {
        if (matched) {
            return;
        }
        // 正则表达式到结尾了
        if (pj == len) {
            if (ti == tlen) {
                matched = true;// 文本串也到结尾了
                return;
            }
        }
        if (pattern[pj] == '*') {
            for (int i = 0; i <= tlen - ti; i++) {
                rmatch(ti + i, pj + 1, text, tlen);
            }
        } else if (pattern[pj] == '?') {
            rmatch(ti, pj + 1, text, tlen);
            rmatch(ti + 1, pj + 1, text, tlen);
        } else if (ti < tlen && pattern[pj] == text[ti]) {
            rmatch(ti + 1, pj + 1, text, tlen);
        }
    }

    public static void main(String[] args) {
        RegularExpression pattern = new RegularExpression("*abcdbc".toCharArray(), 7);
        String text = "abcdbc";
        boolean match = pattern.match(text.toCharArray(), text.length());
        System.out.println(match);

    }

}
