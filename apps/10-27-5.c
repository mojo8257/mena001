#include <stdio.h>
#include <math.h> // fabs

int main(void)
{
    double a, b, c, d, e, f;

    printf("请输入参数 a b c d e f（以空格分隔）：");
    while (1)
    {
        if (scanf("%lf %lf %lf %lf %lf %lf", &a, &b, &c, &d, &e, &f) != 6)
        {
            // 清空无效输入
            int ch;
            while ((ch = getchar()) != '\n' && ch != EOF)
            {
            }
            printf("输入无效，请重新输入 a b c d e f：");
            continue;
        }

        double denom = d + e;
        if (fabs(denom) < 1e-12)
        {
            printf("分母 d+e 不能为 0，请重新输入 a b c d e f：");
            continue;
        }

        // 按公式：S = (a + b*c)/(d + e) + f*f
        double S = (a + b * c) / denom + f * f;

        printf("信号强度 S = %.3f\n", S);
        break;
    }
    return 0;
}
