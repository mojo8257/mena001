#include <stdio.h>

int main(void)
{
    double a, b, c;

    printf("请输入三个数（用空格分隔）：");
    while (1)
    {
        if (scanf("%lf %lf %lf", &a, &b, &c) == 3)
            break;

        // 清空错误输入
        int ch;
        while ((ch = getchar()) != '\n' && ch != EOF)
        {
        }
        printf("输入无效，请重新输入三个数（用空格分隔）：");
    }

    // 方案一：分步使用条件运算符
    double max_ab = (a > b) ? a : b;
    double max_abc = (max_ab > c) ? max_ab : c;

    // // 方案二：一行写法（等价）
    // double max_abc = (a > b) ? ((a > c) ? a : c)
    //                          : ((b > c) ? b : c);

    printf("最大值为：%g\n", max_abc);
    return 0;
}
