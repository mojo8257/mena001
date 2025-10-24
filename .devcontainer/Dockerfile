FROM mcr.microsoft.com/devcontainers/base:bookworm

# 为避免交互提示
ENV DEBIAN_FRONTEND=noninteractive

# 安装 Git LFS（写进镜像层，保证每次重建容器都有）
RUN apt-get update \
    && apt-get install -y --no-install-recommends git-lfs ca-certificates \
    && rm -rf /var/lib/apt/lists/*