#include <stdio.h>

int main(void)
{
    int n;
    printf("请输入一个十进制整数: ");
    scanf("%d", &n);

    printf("十进制数%d<=>八进制数%o\n", n, n);
    printf("十进制数%d<=>十六进制数%x\n", n, n); // 小写 a~f
    return 0;
}
