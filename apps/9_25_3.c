#include <stdio.h>

int main(void)
{
    char ch;
    printf("请输入一个字符: ");
    scanf(" %c", &ch); // 前置空格跳过可能的换行/空格

    printf("前导字符:%c\n", ch - 1);
    printf("该字符:%c\n", ch);
    printf("后续字符:%c\n", ch + 1);
    return 0;
}
