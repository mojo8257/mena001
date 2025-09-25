#include <stdio.h>

int main(void)
{
    int c1, c2;

    printf("请输入两个小写字母(如 a b): ");

    // 读第一个非空白字符
    do
    {
        c1 = getchar();
    } while (c1 == ' ' || c1 == '\n' || c1 == '\t' || c1 == '\r');
    // 读第二个非空白字符
    do
    {
        c2 = getchar();
    } while (c2 == ' ' || c2 == '\n' || c2 == '\t' || c2 == '\r');

    if (c1 >= 'a' && c1 <= 'z')
        c1 = c1 - ('a' - 'A');
    if (c2 >= 'a' && c2 <= 'z')
        c2 = c2 - ('a' - 'A');

    // 大写字母用 putchar 输出；ASCII 数值用 printf 输出
    putchar(c1);
    printf(",");
    putchar(c2);
    printf(",%d,%d\n", c1, c2);

    return 0;
}
