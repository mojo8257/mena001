#include <stdio.h>

int main(void)
{
    char ch;
    printf("请输入一个英文字母：");
    if (scanf(" %c", &ch) != 1)
        return 0;

    // 使用条件运算符实现大小写转换
    char converted =
        (ch >= 'A' && ch <= 'Z') ? (ch + ('a' - 'A')) : (ch >= 'a' && ch <= 'z') ? (ch - ('a' - 'A'))
                                                                                 : ch;

    if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
        printf("转换结果：%c\n", converted);
    else
        printf("输入的不是英文字母，原样输出：%c\n", ch);

    return 0;
}
