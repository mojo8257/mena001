#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void)
{
    int n;

    printf("请输入一个3位整数：");
    while (1)
    {
        if (scanf("%d", &n) != 1)
        {
            // 清空无效输入
            int ch;
            while ((ch = getchar()) != '\n' && ch != EOF)
            {
            }
            printf("输入无效，请输入一个3位整数：");
            continue;
        }

        // 忽略正负号：取绝对值

        int x = abs(n);

        // 校验是否为三位整数
        if (x < 100 || x > 999)
        {
            printf("这不是3位整数，请重新输入：");
            continue;
        }

        // 分离百位、十位、个位
        int bai = x / 100;       // 百位
        int shi = (x / 10) % 10; // 十位
        int ge = x % 10;         // 个位

        // 逆序：个位*100 + 十位*10 + 百位
        int rev = ge * 100 + shi * 10 + bai;

        printf("其逆序数为：%d\n", rev);
        break;
    }

    return 0;
}