FROM java:17.0
MAINTAINER ningle
ADD library-java-1.0.jar /library-java.jar
# 设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
RUN bash -c 'touch /library-java.jar'
ENV JAVA_OPTS="\
-Xms128M \
-Xmx196M \
-XX:MetaspaceSize=128M \
-XX:MaxMetaspaceSize=196M"
ENTRYPOINT java ${JAVA_OPTS} -jar /library-java.jar
EXPOSE 15100
