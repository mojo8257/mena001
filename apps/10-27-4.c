#include <stdio.h>

int main(void)
{
    double x;

    // 交互式输入并校验
    printf("请输入一个实数：");
    while (scanf("%lf", &x) != 1)
    {
        int ch;
        while ((ch = getchar()) != '\n' && ch != EOF)
        {
        } // 清空无效输入
        printf("输入无效，请重新输入一个实数：");
    }

    // 方法 a：格式控制符 %.2f（输出时已按“就近取整”保留两位）
    printf("方法 a（格式控制符 %%.2f）：%.2f\n", x);

    // 方法 b：数学运算 + 强制类型转换
    // 思路：y = (x*100 + 0.5) 再取整（正数），负数用 (x*100 - 0.5) 再取整，最后 /100.0
    // 说明：C 的 (int) 对正负数都是“向零截断”
    double y;
    if (x >= 0)
    {
        long long t = (long long)(x * 100.0 + 0.5);
        y = t / 100.0;
    }
    else
    {
        long long t = (long long)(x * 100.0 - 0.5);
        y = t / 100.0;
    }
    printf("方法 b（数学运算+强制类型转换）：%.2f\n", y);

    return 0;
}
