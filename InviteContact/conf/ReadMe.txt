*HOW TO RUN -->
1-Install Apache Active MQ
        (d)run activemq(goto apache-activemq-5.7.0/bin/ and run ./activemq start)    +stop command( ./activemq stop)
        (e) monitor queue by browser :http://ip:8161/admin/queues.jsp


2-Run java process
        (a)unzip pokktintf.zip
        (b)change configurations (config.properties,log4j.xml)
        (c)run commands :-
                --> export CLASSPATH=.:lib/*:$CLASSPATH
                --> java com.rh.main.StartApp &




3- Java configuration
        config.properties-> configure datbase and queues
        log4.xml-> path of logs
