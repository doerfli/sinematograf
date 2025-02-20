FROM mcr.microsoft.com/devcontainers/base:bookworm

ARG JAVA_VARIANT=temurin
ARG JAVA_VERSION=23
ARG QUARKUS_VERSION=3.18
ARG MAVEN_VERSION=3

USER vscode

RUN curl https://mise.run | sh
RUN echo 'eval "$(~/.local/bin/mise activate zsh)"' >> ~/.zshrc

RUN /home/vscode/.local/bin/mise install -y java@${JAVA_VARIANT}-${JAVA_VERSION}
RUN /home/vscode/.local/bin/mise use -g java@$JAVA_VARIANT-$JAVA_VERSION

RUN /home/vscode/.local/bin/mise install -y quarkus@$QUARKUS_VERSION
RUN /home/vscode/.local/bin/mise use -g quarkus@$QUARKUS_VERSION

RUN /home/vscode/.local/bin/mise install -y maven@$MAVEN_VERSION
RUN /home/vscode/.local/bin/mise use -g maven@$MAVEN_VERSION


# [Optional] Uncomment this section to install additional OS packages.
# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#     && apt-get -y install --no-install-recommends <your-package-list-here>

# [Optional] Uncomment this line to install global node packages.
# RUN su vscode -c "source /usr/local/share/nvm/nvm.sh && npm install -g <your-package-here>" 2>&1
