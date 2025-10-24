FROM mcr.microsoft.com/devcontainers/cpp:ubuntu-22.04

ENV DEBIAN_FRONTEND=noninteractive

# 固化你要的工具；如需钉住具体版本可用：valgrind=1:3.19.0-1ubuntu1 等（注意版本号需与仓库实际匹配）
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
       valgrind ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# 如果你编译时需要特定 locale/时区，也可在这里设置（可选）
# RUN apt-get update && apt-get install -y locales && \
#     sed -i 's/# zh_TW.UTF-8/zh_TW.UTF-8/' /etc/locale.gen && locale-gen zh_TW.UTF-8
# ENV LANG=zh_TW.UTF-8 LC_ALL=zh_TW.UTF-8
